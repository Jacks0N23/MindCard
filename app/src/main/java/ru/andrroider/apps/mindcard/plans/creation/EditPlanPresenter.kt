package ru.andrroider.apps.mindcard.plans.creation

import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.andrroider.apps.business.plans.AddPlanInteractor
import ru.andrroider.apps.business.plans.GetPlansByIdInteractor
import ru.andrroider.apps.business.plans.tasks.UpdateInteractor
import ru.andrroider.apps.data.db.Plans
import ru.andrroider.apps.mindcard.base.BaseMvpPresenter

/**
 * Created by Jackson on 07/02/2018.
 */
@InjectViewState
class EditPlanPresenter(private val addPlanInteractor: AddPlanInteractor,
                        private val getPlansByIdInteractor: GetPlansByIdInteractor,
                        private val updateInteractor: UpdateInteractor) : BaseMvpPresenter<NewPlanView>() {

    fun addNewItem(plan: Plans) {
        addSubscription(addPlanInteractor(plan).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribe({ viewState.finishAfterCreation() }, viewState::showError))
    }

    fun loadTaskForEditing(itemId: Long) {
        addSubscription(getPlansByIdInteractor(itemId).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribe(viewState::fillForEditing, viewState::showError))
    }

    fun updateItem(plan: Plans) {
        addSubscription(updateInteractor(plan)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ viewState.finishAfterCreation() }, viewState::showError))
    }
}