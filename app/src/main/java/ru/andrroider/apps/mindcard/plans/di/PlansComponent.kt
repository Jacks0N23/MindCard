package ru.andrroider.apps.mindcard.plans.di

import android.app.Activity
import android.support.v4.app.FragmentManager
import android.support.v7.widget.PopupMenu
import android.view.Gravity
import android.view.View
import android.widget.TextView
import ru.andrroider.apps.business.plans.PlanUi
import ru.andrroider.apps.data.ParametrizedAction
import ru.andrroider.apps.data.ViewTyped
import ru.andrroider.apps.mindcard.R
import ru.andrroider.apps.mindcard.extentions.asType
import ru.andrroider.apps.mindcard.extentions.findView
import ru.andrroider.apps.mindcard.plans.PlansHolderFactory
import ru.andrroider.apps.mindcard.plans.tasks.newTasksInstance
import ru.andrroider.apps.mindcard.widget.createDeleteConfirmationDialog
import ru.andrroider.apps.mindcard.widget.recyclerView.Adapter

class PlansComponent(private val activity: Activity?,
                     private val fragmentManager: FragmentManager?,
                     private val deleteCardAction: ParametrizedAction<Long>,
                     private val editCardAction: ParametrizedAction<Long>) {

    private val plansItems = mutableListOf<ViewTyped>()
    private val onCardClickListener: (View) -> Unit = {
        fragmentManager?.beginTransaction()
                ?.replace(R.id.base_container,
                        newTasksInstance(it.tag.asType(), it.findView<TextView>(R.id.itemTitle).text))
                ?.addToBackStack(null)
                ?.commit()
    }
    private val onCardLongClickListener: (View) -> Boolean = { view ->
        popupMenu(view)?.show()
        true
    }
    private val onContextMenuItemClickListener = { itemId: Int, cardId: Long ->
        when (itemId) {
            R.id.editCard -> editCardAction(cardId)
            R.id.deleteCard -> deleteConfirmationDialog(cardId)
            else -> throw NoSuchElementException()
        }
    }
    private val deleteConfirmationDialog = { cardId: Long ->
        activity?.createDeleteConfirmationDialog({ deleteCardAction(cardId) })
                ?.show()
    }
    private val popupMenu = { view: View ->
        activity?.let { activity ->
            PopupMenu(activity, view, Gravity.END).apply {
                inflate(R.menu.card_context_menu)
                setOnMenuItemClickListener {
                    onContextMenuItemClickListener(it.itemId, view.tag.asType())
                    true
                }
            }
        }
    }
    val adapter = Adapter<PlanUi>(plansItems,
            holderFactory = PlansHolderFactory(onCardClickListener, onCardLongClickListener))
}