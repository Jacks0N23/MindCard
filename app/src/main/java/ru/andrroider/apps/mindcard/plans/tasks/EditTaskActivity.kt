package ru.andrroider.apps.mindcard.plans.tasks

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.WindowManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import kotlinx.android.synthetic.main.activity_edit_task.*
import ru.andrroider.apps.data.db.Plans
import ru.andrroider.apps.mindcard.R
import ru.andrroider.apps.mindcard.base.BaseMvpActivity
import ru.andrroider.apps.mindcard.di.AppComponentInjector
import ru.andrroider.apps.mindcard.extentions.getDarkerColor
import ru.andrroider.apps.mindcard.extentions.setAfterTextChangedAction
import ru.andrroider.apps.mindcard.plans.creation.EditPlanPresenter
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

class NewTaskActivity : BaseMvpActivity(R.layout.activity_edit_task),
        NewPlanView {

    @InjectPresenter
    lateinit var presenter: EditPlanPresenter

    @ProvidePresenter
    fun providePresenter(): EditPlanPresenter = AppComponentInjector.component().newPlanPresenter()

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
    private val colorPickerBuilder = ColorPickerDialog.newBuilder()
    private var preselectedColor: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
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
        taskColorContainer.setOnClickListener { createColorPicker().show(fragmentManager, "") }
    }

    private fun saveWithBlankCheck() {
        when {
            taskTitle.text.isNullOrBlank() -> taskTitleContainer.error = getString(R.string.title_error)
            taskId > -1 -> presenter.updateItem(
                    taskTitle.text.toString(),
                    taskDescription.text.toString(),
                    taskId,
                    taskColor.backgroundTintList!!.defaultColor,
                    planId)
            else -> {
                val planId = intent.getLongExtra(PLAN_ID, -1)
                presenter.addNewItem(
                        taskTitle.text.toString(),
                        taskDescription.text.toString(),
                        taskColor.backgroundTintList!!.defaultColor,
                        planId)
            }
        }
    }

    override fun fillForEditing(plans: Plans) {
        taskTitle.setText(plans.title)
        taskTitle.setSelection(taskTitle.length())
        taskDescription.setText(plans.description)
        val colorInt = plans.colorInt
        taskColor.backgroundTintList = ColorStateList.valueOf(colorInt)
        preselectedColor = colorInt
        dyeColorElements(preselectedColor)
    }

    private fun createColorPicker(): ColorPickerDialog {
        colorPickerBuilder
                .setDialogTitle(R.string.pick_color)
                .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                .setPresets(resources.getIntArray(R.array.task_colors))
                .setAllowCustom(false)
                .setDialogId(0)
                .setColor(preselectedColor)
                .setSelectedButtonText(R.string.pick_color_select)
        val colorPickerDialog = colorPickerBuilder.create()
        colorPickerDialog.setColorPickerDialogListener(object : AbstractColorPickerDialogListener() {
            override fun onColorSelected(dialogId: Int, color: Int) {
                dyeColorElements(color)
            }
        })
        return colorPickerDialog
    }

    private fun dyeColorElements(color: Int) {
        val colorStateList = ColorStateList.valueOf(color)
        taskColor.backgroundTintList = colorStateList
        appbarPlanEdit.backgroundTintList = colorStateList
        val darkerColor = color.getDarkerColor()
        saveTask.backgroundTintList = colorStateList
        window.statusBarColor = darkerColor
        preselectedColor = color
    }

    override fun finishAfterCreation() {
        finish()
    }

    override fun showError(throwable: Throwable) {
        showErrorWithSnackbar(throwable)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        quitDialog.show()
    }
}