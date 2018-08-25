package ru.andrroider.apps.mindcard.plans

import com.arellomobile.mvp.MvpView
import ru.andrroider.apps.business.plans.PlanUi

/**
 * Created by Jackson on 03/02/2018.
 */
interface PlansView : MvpView {
    fun showPlans(plans: List<PlanUi>)
    fun showEmptyPlans()
    fun showError(throwable: Throwable)
    fun planSuccessfullyDeleted(indexOfDeletedItem: Int)
}