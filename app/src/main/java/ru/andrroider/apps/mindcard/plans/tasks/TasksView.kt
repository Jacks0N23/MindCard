package ru.andrroider.apps.mindcard.plans.tasks

import com.arellomobile.mvp.MvpView
import ru.andrroider.apps.business.plans.TaskUi

interface TasksView : MvpView {
    fun showTasks(tasks: List<TaskUi>)
    fun showError(throwable: Throwable)
}