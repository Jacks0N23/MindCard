package ru.andrroider.apps.mindcard.plans.tasks

import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.andrroider.apps.business.plans.TaskUi
import ru.andrroider.apps.business.plans.tasks.DeleteInteractor
import ru.andrroider.apps.business.plans.tasks.GetTasksByPlanIdInteractor
import ru.andrroider.apps.mindcard.base.BaseMvpPresenter

/**
 * Created by Jackson on 25/03/2018.
 */
@InjectViewState
class TasksPresenter(private val getTasksByPlanIdInteractor: GetTasksByPlanIdInteractor,
                     private val deleteInteractor: DeleteInteractor) : BaseMvpPresenter<TasksView>() {

    private var tasks = listOf<TaskUi>()
    fun loadAllTasksByPlanId(planId: Long?) {
        addSubscription(getTasksByPlanIdInteractor(planId).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribe(
                {
                    tasks = it
                    viewState.showTasks(tasks)
                },
                viewState::showError))
    }

    fun deleteTask(taskId: Long) {
        addSubscription(deleteInteractor(taskId).toObservable<Boolean>().map {
            tasks.indexOfFirst { it.id == taskId }
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            viewState.taskSuccessfullyDeleted(it)
        }, viewState::showError))
    }
}