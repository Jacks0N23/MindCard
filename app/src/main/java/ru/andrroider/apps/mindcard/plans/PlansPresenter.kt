package ru.andrroider.apps.mindcard.plans

import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.andrroider.apps.business.plans.GetAllPlansInteractor
import ru.andrroider.apps.business.plans.PlanUi
import ru.andrroider.apps.business.plans.tasks.DeleteInteractor
import ru.andrroider.apps.mindcard.base.BaseMvpPresenter

/**
 * Created by Jackson on 03/02/2018.
 */
@InjectViewState
class PlansPresenter(private val getAllPlansInteractor: GetAllPlansInteractor,
                     private val deleteInteractor: DeleteInteractor) : BaseMvpPresenter<PlansView>() {

    private var plans = listOf<PlanUi>()

    fun loadAllPlans() {
        val disposable = getAllPlansInteractor().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                               plans = it
                               viewState.showPlans(plans)
                           }, viewState::showError)
        addSubscription(disposable)
    }

    fun deletePlan(planId: Long) {
        val disposable = deleteInteractor(planId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                               viewState.planSuccessfullyDeleted(plans.indexOfFirst { it.id == planId })
                           }, viewState::showError)
        addSubscription(disposable)
    }
}