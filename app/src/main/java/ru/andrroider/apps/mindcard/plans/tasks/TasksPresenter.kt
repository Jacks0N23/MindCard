package ru.andrroider.apps.mindcard.plans.tasks

import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.andrroider.apps.business.plans.GetTasksByPlanIdInteractor
import ru.andrroider.apps.mindcard.base.BaseMvpPresenter

/**
 * Created by Jackson on 25/03/2018.
 */
@InjectViewState
class TasksPresenter(private val getTasksByPlanIdInteractor: GetTasksByPlanIdInteractor) : BaseMvpPresenter<TasksView>() {

    fun loadAllTasksByPlanId(planId: Long?) {
        addSubscription(getTasksByPlanIdInteractor(planId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
                    viewState.showTasks(it)
                }, viewState::showError))
    }
}