package ru.andrroider.apps.mindcard.plans.creation

import com.arellomobile.mvp.MvpView

/**
 * Created by Jackson on 07/02/2018.
 */
interface NewPlanView : MvpView {
    fun finishAfterCreation()
    fun showError(throwable: Throwable)
}