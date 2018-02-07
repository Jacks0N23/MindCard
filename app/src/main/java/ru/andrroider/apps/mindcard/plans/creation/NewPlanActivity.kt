package ru.andrroider.apps.mindcard.plans.creation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_new_plan.*
import ru.andrroider.apps.mindcard.R
import ru.andrroider.apps.mindcard.base.BaseMvpActivity
import ru.andrroider.apps.mindcard.di.AppComponentInjector
import ru.andrroider.apps.mindcard.extentions.setAfterTextChangedAction

/**
 * Created by Jackson on 07/02/2018.
 */

fun startNewPlanActivity(context: Context) {
    context.startActivity(Intent(context, NewPlanActivity::class.java))
}

class NewPlanActivity : BaseMvpActivity(R.layout.activity_new_plan), NewPlanView {

    @InjectPresenter
    lateinit var presenter: NewPlanPresenter

    @ProvidePresenter
    fun providePresenter(): NewPlanPresenter = AppComponentInjector.component().newPlanPresenter()

    private val quitDialog by lazy {
        AlertDialog.Builder(this@NewPlanActivity)
                .setTitle(R.string.save)
                .setPositiveButton(R.string.yes, { _, _ ->
                    saveWithBlankCheck()
                })
                .setNegativeButton(R.string.no, { _, _ ->
                    finish()
                }).create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        savePlan.setOnClickListener { saveWithBlankCheck() }
        planTitle.setAfterTextChangedAction { text ->
            planTitleContainer.error = if (text.isNullOrBlank()) getString(R.string.title_error) else ""
        }
    }

    override fun finishAfterCreation() {
        finish()
    }

    override fun showError(throwable: Throwable) {
        showErrorWithSnackbar(throwable)
    }

    private fun saveWithBlankCheck() {
        if (planTitle.text.isBlank())
            planTitleContainer.error = getString(R.string.title_error)
        else
            presenter.addNewItem(planTitle.text.toString(), planDescription.text.toString())
    }

    override fun onBackPressed() {
        quitDialog.show()
    }
}
