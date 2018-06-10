package ru.andrroider.apps.mindcard.plans.tasks

import ru.andrroider.apps.business.plans.TaskUi
import ru.andrroider.apps.data.db.Plans
import ru.andrroider.apps.mindcard.R

class TasksToTaskUI : (List<Plans>) -> List<TaskUi> {
    override fun invoke(plans: List<Plans>): List<TaskUi> {
        return plans.map { TaskUi(it.id, it.title, it.colorInt, it.description, R.layout.item_task) }
    }
}