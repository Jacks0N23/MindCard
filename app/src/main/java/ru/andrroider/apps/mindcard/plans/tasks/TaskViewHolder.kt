package ru.andrroider.apps.mindcard.plans.tasks

import android.view.View
import android.widget.TextView
import ru.andrroider.apps.business.plans.TaskUi
import ru.andrroider.apps.mindcard.R
import ru.andrroider.apps.mindcard.extentions.findView
import ru.andrroider.apps.mindcard.extentions.hide
import ru.andrroider.apps.mindcard.extentions.show
import ru.andrroider.apps.mindcard.widget.recyclerView.BaseViewHolder

class TaskViewHolder(view: View, onCardClickListener: (View) -> Unit) : BaseViewHolder<TaskUi>(view) {

    private val title: TextView = view.findView(R.id.planTitle)
    private val description: TextView = view.findView(R.id.planDescription)

    init {
        itemView.setOnClickListener(onCardClickListener)
    }

    override fun bind(item: TaskUi) {
        title.text = item.title
        itemView.tag = item.id
        if (item.description.isNotBlank()) {
            description.show()
            description.text = item.description
        } else {
            description.hide()
        }
    }
}