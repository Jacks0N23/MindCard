package ru.andrroider.apps.mindcard.plans.creation

import com.arellomobile.mvp.MvpView
import ru.andrroider.apps.data.db.Plans

/**
 * Created by Jackson on 07/02/2018.
 */
interface NewPlanView : MvpView {

    fun finishAfterCreation()
    fun showError(throwable: Throwable)
    fun fillForEditing(plans: Plans)
}