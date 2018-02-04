package ru.andrroider.apps.mindcard.widget.recyclerView

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import ru.andrroider.apps.data.*
import ru.andrroider.apps.mindcard.R

/**
 * Created by Jackson on 03/02/2018.
 */
object LoadMoreItem : ViewTyped {
    override val viewType: Int = R.layout.item_loading
}

object ProgressItem : ViewTyped {
    override val viewType: Int = R.layout.item_loading
}

class EmptyView(override val viewType: Int) : ViewTyped

class EmptyViewHolder(itemView: View) : BaseViewHolder<EmptyView>(itemView)

interface PageAdapter {
    val items: MutableList<ViewTyped>
    val adapter: AdapterCallbacks

    fun showLoader() {
        items.add(LoadMoreItem)
        adapter.notifyItemInserted(items.size)
    }

    fun hideLoader() {
        removeLoader()
        adapter.notifyDataSetChanged()
    }

    fun addItems(newItems: List<ViewTyped>) {
        if (newItems.isEmpty()) {
            removeLoader()
        } else {
            items.removeAt(items.size - 1)
            items.addAll(newItems)
            adapter.notifyItemRangeChanged(items.size - newItems.size, newItems.size)
        }
    }

    private fun removeLoader() {
        items.removeAt(items.size - 1)
        adapter.notifyItemRemoved(items.size + 1)
    }
}

interface AdapterCallbacks {
    fun notifyItemInserted(position: Int)
    fun notifyItemRemoved(position: Int)
    fun notifyItemRangeChanged(startPosition: Int, count: Int)
    fun notifyDataSetChanged()
}
open class Adapter<T : ViewTyped>(override val items: MutableList<ViewTyped> = mutableListOf(),
                                  private val emptyList: ViewTyped? = null,
                                  private val emptyError: ViewTyped? = null,
                                  private val holderFactory: HolderFactory) : RecyclerView.Adapter<BaseViewHolder<ViewTyped>>(),
        PageAdapter,
        AdapterCallbacks {

    override val adapter: AdapterCallbacks = this
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewTyped> = holderFactory.invoke(
            parent,
            viewType)

    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: BaseViewHolder<ViewTyped>, position: Int) = holder.bind(items[position])

    fun setItems(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun getItem(position: Int): T = items[position] as T

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    fun updateItems(item: List<T>) {
        item.forEach {
            val position = items.indexOf(it)
            if (position >= 0) {
                items[position] = it
                notifyItemRemoved(position)
                notifyItemInserted(position)
            }
        }
    }

    fun showEmptyList() {
        if (emptyList == null) {
            return
        }
        items.clear()
        items.add(emptyList)
        notifyDataSetChanged()
    }

    fun showErrorEmptyView() {
        items.clear()
        emptyError?.let { items.add(it) }
        notifyDataSetChanged()
    }

    fun showLoadingView() {
        this.items.clear()
        this.items.add(ProgressItem)
        notifyDataSetChanged()
    }

    fun addToTop(newItems: List<T>) {
        val hasEmptyView = items.size == 1 && (items.contains(emptyList) || items.contains(emptyError))
        if (hasEmptyView) {
            items.clear()
            notifyItemRemoved(0)
        }
        this.items.addAll(0, newItems)
        notifyItemRangeInserted(0, newItems.size)
    }

    fun update(updates: Updates<T>) {
        when (updates) {
            is Add -> {
                if (updates.updatedItems.isEmpty()) {
                    showEmptyList()
                } else {
                    setItems(updates.updatedItems)
                }
            }
            is Inserted -> updates.run {
                if (position == 0 || !toEnd) {
                    addToTop(updatedItems)
                } else {
                    addItems(updatedItems)
                }
            }
            is Moved -> updates.run {
                items.removeAt(oldPosition)
                items.addAll(position, updatedItems)
                if (oldPosition == position) {
                    notifyItemChanged(position)
                } else {
                    notifyItemMoved(oldPosition, position)
                }
            }
        }
    }

    fun removeItem(position: Int): T {
        val item = items.removeAt(position) as T
        notifyItemRemoved(position)
        return item
    }

    fun reverseChangeStatus(toReverseChangeStatus: List<Pair<Int, T>>) {
        toReverseChangeStatus.asReversed().forEach { (position, item) ->
            items.add(position, item)
            notifyItemInserted(position)
        }
    }
}