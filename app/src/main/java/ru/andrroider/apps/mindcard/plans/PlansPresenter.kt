package ru.andrroider.apps.mindcard.plans

import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.andrroider.apps.business.plans.GetPlansInteractor
import ru.andrroider.apps.mindcard.base.BaseMvpPresenter

/**
 * Created by Jackson on 03/02/2018.
 */
@InjectViewState
class PlansPresenter(private val getPlansInteractor: GetPlansInteractor) : BaseMvpPresenter<PlansView>() {

    fun loadAllPlans() {
        addSubscription(getPlansInteractor().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
            viewState.showPlans(it)
        }, viewState::showError))
    }
}