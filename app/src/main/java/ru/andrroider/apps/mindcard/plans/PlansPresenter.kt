package ru.andrroider.apps.mindcard.plans

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.andrroider.apps.business.plans.AddPlanInteractor
import ru.andrroider.apps.business.plans.GetPlansInteractor
import ru.andrroider.apps.data.db.Plans

/**
 * Created by Jackson on 03/02/2018.
 */
@InjectViewState
class PlansPresenter(private val getPlansInteractor: GetPlansInteractor,
                     private val addPlanInteractor: AddPlanInteractor): MvpPresenter<PlansView>() {
    //temp
    private var lastAddedNum = 0L

    fun loadAllPlans() {
        getPlansInteractor().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({
            lastAddedNum = if (it.isNotEmpty()) it.last().id else 0
            viewState.showPlans(it)
        }, viewState::showError)
    }

    //FIXME
    fun addNewItem() {
        lastAddedNum++
        val plan = Plans("title#$lastAddedNum", "description#$lastAddedNum")
        addPlanInteractor(plan).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe({ }, {})
    }
}