package ru.andrroider.apps.mindcard.plans.creation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_new_plan.*
import ru.andrroider.apps.data.db.Plans
import ru.andrroider.apps.mindcard.R
import ru.andrroider.apps.mindcard.base.BaseMvpActivity
import ru.andrroider.apps.mindcard.di.AppComponentInjector
import ru.andrroider.apps.mindcard.extentions.setAfterTextChangedAction

/**
 * Created by Jackson on 07/02/2018.
 */
const val PLAN_EDIT_ID = "PLAN_EDIT_ID"

fun startNewPlanActivity(context: Context, planToEditId: Long? = null) {
    val intent = Intent(context, NewPlanActivity::class.java)
    intent.putExtra(PLAN_EDIT_ID, planToEditId)
    context.startActivity(intent)
}

class NewPlanActivity : BaseMvpActivity(R.layout.activity_new_plan),
                        NewPlanView {

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
            })
            .create()
    }
    private val planEditId by lazy { intent.getLongExtra(PLAN_EDIT_ID, -1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        if (planEditId > -1) {
            presenter.loadTaskForEditing(planEditId)
        }
        saveTask.setOnClickListener { saveWithBlankCheck() }
        itemTitle.setAfterTextChangedAction { text ->
            taskTitleContainer.error = if (text.isNullOrBlank()) getString(R.string.title_error) else ""
        }
    }

    override fun fillForEditing(plans: Plans) {
        itemTitle.setText(plans.title)
        itemDescription.setText(plans.description)
    }

    override fun finishAfterCreation() {
        finish()
    }

    override fun showError(throwable: Throwable) {
        showErrorWithSnackbar(throwable)
    }

    private fun saveWithBlankCheck() {
        when {
            planEditId > -1 -> presenter.updateItem(itemTitle.text.toString(), itemDescription.text.toString(),
                    planEditId)
            itemTitle.text.isNullOrBlank() -> taskTitleContainer.error = getString(R.string.title_error)
            else -> {
                presenter.addNewItem(itemTitle.text.toString(), itemDescription.text.toString())
            }
        }
    }

    override fun onBackPressed() {
        quitDialog.show()
    }
}
