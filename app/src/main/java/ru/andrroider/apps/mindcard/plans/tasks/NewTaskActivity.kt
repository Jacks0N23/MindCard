package ru.andrroider.apps.mindcard.plans.tasks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_new_task.*
import ru.andrroider.apps.data.db.Plans
import ru.andrroider.apps.mindcard.R
import ru.andrroider.apps.mindcard.base.BaseMvpActivity
import ru.andrroider.apps.mindcard.di.AppComponentInjector
import ru.andrroider.apps.mindcard.extentions.setAfterTextChangedAction
import ru.andrroider.apps.mindcard.plans.creation.NewPlanPresenter
import ru.andrroider.apps.mindcard.plans.creation.NewPlanView

/**
 * Created by Jackson on 27/03/2018.
 */
const val PLAN_ID = "PLAN_ID"
const val EDIT_TASK_ID = "EDIT_TASK_ID"

fun startNewTaskActivity(context: Context, planId: Long? = null, editItemId: Long? = null) {
    val intent = Intent(context, NewTaskActivity::class.java)
    intent.putExtra(PLAN_ID, planId)
    intent.putExtra(EDIT_TASK_ID, editItemId)
    context.startActivity(intent)
}

class NewTaskActivity : BaseMvpActivity(R.layout.activity_new_task),
        NewPlanView {

    @InjectPresenter
    lateinit var presenter: NewPlanPresenter

    @ProvidePresenter
    fun providePresenter(): NewPlanPresenter = AppComponentInjector.component().newPlanPresenter()

    private val quitDialog by lazy {
        AlertDialog.Builder(this)
                .setTitle(R.string.save)
                .setPositiveButton(R.string.yes, { _, _ ->
                    saveWithBlankCheck()
                })
                .setNegativeButton(R.string.no, { _, _ ->
                    finish()
                })
                .create()
    }
    private val planId by lazy { intent.getLongExtra(PLAN_ID, -1) }
    private val taskId by lazy { intent.getLongExtra(EDIT_TASK_ID, -1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        if (taskId > -1) {
            presenter.loadTaskForEditing(taskId)
        }
        saveTask.setOnClickListener { saveWithBlankCheck() }
        taskTitle.setAfterTextChangedAction { text ->
            taskTitleContainer.error = if (text.isNullOrBlank()) getString(R.string.title_error) else ""
        }
    }

    override fun fillForEditing(plans: Plans) {
        taskTitle.setText(plans.title)
        taskTitle.setSelection(taskTitle.length())
        taskDescription.setText(plans.description)
    }

    override fun finishAfterCreation() {
        finish()
    }

    override fun showError(throwable: Throwable) {
        showErrorWithSnackbar(throwable)
    }

    private fun saveWithBlankCheck() {
        when {
            taskTitle.text.isNullOrBlank() -> taskTitleContainer.error = getString(R.string.title_error)
            taskId > -1 -> presenter.updateItem(taskTitle.text.toString(), taskDescription.text.toString(),
                    taskId, planId)
            else -> {
                val planId = intent.getLongExtra(PLAN_ID, -1)
                presenter.addNewItem(taskTitle.text.toString(), taskDescription.text.toString(), planId)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        quitDialog.show()
    }
}