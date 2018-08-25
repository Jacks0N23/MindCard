package ru.andrroider.apps.mindcard.plans.tasks

import com.arellomobile.mvp.MvpView
import ru.andrroider.apps.business.plans.TaskUi

interface TasksView : MvpView {
    fun reloadData(title: String, planId: Long)
    fun showTasks(tasks: List<TaskUi>)
    fun showEmptyTasks()
    fun showError(throwable: Throwable)
    fun taskSuccessfullyDeleted(indexOfDeletedItem: Int)
}