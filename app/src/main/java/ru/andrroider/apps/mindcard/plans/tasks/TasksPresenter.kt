package ru.andrroider.apps.mindcard.plans.tasks

import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.andrroider.apps.business.plans.TaskUi
import ru.andrroider.apps.business.plans.tasks.DeleteInteractor
import ru.andrroider.apps.business.plans.tasks.GetTasksByPlanIdInteractor
import ru.andrroider.apps.mindcard.base.BaseMvpPresenter
import java.util.LinkedList

/**
 * Created by Jackson on 25/03/2018.
 */
@InjectViewState
class TasksPresenter(private val getTasksByPlanIdInteractor: GetTasksByPlanIdInteractor,
                     private val deleteInteractor: DeleteInteractor) : BaseMvpPresenter<TasksView>() {

    private var tasks = listOf<TaskUi>()
    private val parentPlanIdsStack = LinkedList<Long>()
    private val parentPlanTitlesStack = LinkedList<String>()

    fun addPlanToNodeAndLoadData(title: String, planId: Long) {
        parentPlanIdsStack.add(planId)
        parentPlanTitlesStack.add(title)
        loadAllTasksByPlanId(planId)
        viewState.reloadData(title, planId)
    }

    fun popPlanAndReloadData(): Boolean {
        disposeSubscriptions()
        val isLastLayer = parentPlanIdsStack.size == 1
        if (parentPlanIdsStack.size > 1) {
            parentPlanIdsStack.removeLast()
            loadAllTasksByPlanId(parentPlanIdsStack.last)
        }
        if (parentPlanTitlesStack.size > 1) {
            parentPlanTitlesStack.removeLast()
        }
        viewState.reloadData(parentPlanTitlesStack.last, parentPlanIdsStack.last)
        if (isLastLayer) parentPlanIdsStack.removeLast()
        return isLastLayer
    }

    fun deleteTask(taskId: Long) {
        val disposable = deleteInteractor(taskId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                               viewState.taskSuccessfullyDeleted(tasks.indexOfFirst { it.id == taskId })
                           }, viewState::showError)
        addSubscription(disposable)
    }

    private fun loadAllTasksByPlanId(planId: Long) {
        val disposable = getTasksByPlanIdInteractor(planId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                               tasks = it

                               viewState.showTasks(tasks)
                           }, viewState::showError)
        addSubscription(disposable)
    }
}