package ru.andrroider.apps.mindcard.plans.tasks

import android.content.res.ColorStateList
import android.support.design.widget.FloatingActionButton
import android.view.View
import android.widget.TextView
import ru.andrroider.apps.business.plans.TaskUi
import ru.andrroider.apps.mindcard.R
import ru.andrroider.apps.mindcard.extentions.findView
import ru.andrroider.apps.mindcard.extentions.hide
import ru.andrroider.apps.mindcard.extentions.show
import ru.andrroider.apps.mindcard.widget.recyclerView.BaseViewHolder

class TaskViewHolder(view: View,
                     onCardClickListener: (View) -> Unit,
                     onCardLongClickListener: (View) -> Boolean) : BaseViewHolder<TaskUi>(view) {

    private val title: TextView = view.findView(R.id.itemTitle)
    private val description: TextView = view.findView(R.id.itemDescription)
    private val taskColor: FloatingActionButton = view.findView(R.id.taskColor)

    init {
        itemView.setOnClickListener(onCardClickListener)
        itemView.setOnLongClickListener(onCardLongClickListener)
    }

    override fun bind(item: TaskUi) {
        title.text = item.title
        taskColor.backgroundTintList = ColorStateList.valueOf(item.color)
        itemView.tag = item.id
        if (item.description.isNotBlank()) {
            description.show()
            description.text = item.description
        } else {
            description.hide()
        }
    }
}