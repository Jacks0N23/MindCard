package ru.andrroider.apps.mindcard.plans.creation

import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.andrroider.apps.business.plans.AddPlanInteractor
import ru.andrroider.apps.data.db.Plans
import ru.andrroider.apps.mindcard.base.BaseMvpPresenter

/**
 * Created by Jackson on 07/02/2018.
 */
@InjectViewState
class NewPlanPresenter(private val addPlanInteractor: AddPlanInteractor) : BaseMvpPresenter<NewPlanView>() {

    fun addNewItem(title: String, description: String) {
        val plan = Plans(title, description)
        addSubscription(addPlanInteractor(plan).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ viewState.finishAfterCreation() },
                        { viewState.showError(it) }))
    }
}