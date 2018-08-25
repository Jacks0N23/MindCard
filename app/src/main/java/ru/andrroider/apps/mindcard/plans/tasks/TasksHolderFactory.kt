package ru.andrroider.apps.mindcard.plans.tasks

import android.view.View
import android.view.ViewGroup
import ru.andrroider.apps.data.ViewTyped
import ru.andrroider.apps.mindcard.R
import ru.andrroider.apps.mindcard.extentions.asType
import ru.andrroider.apps.mindcard.extentions.inflate
import ru.andrroider.apps.mindcard.widget.recyclerView.BaseViewHolder
import ru.andrroider.apps.mindcard.widget.recyclerView.HolderFactory

class TasksHolderFactory(private val onCardClickListener: (View) -> Unit,
                         private val onCardLongClickListener: (View) -> Boolean) : HolderFactory {

    override fun invoke(viewGroup: ViewGroup, viewType: Int): BaseViewHolder<ViewTyped> {
        val view = viewGroup.inflate(viewType)
        return when (viewType) {
            R.layout.item_task -> {
                TaskViewHolder(view, onCardClickListener, onCardLongClickListener)
            }
            R.layout.item_empty_tasks -> BaseViewHolder<Unit>(view)
            else -> throw IllegalStateException("unknown viewType")
        }.asType()
    }
}