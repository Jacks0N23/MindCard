package ru.andrroider.apps.mindcard.plans.tasks

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.text.format.DateFormat
import android.view.WindowManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import kotlinx.android.synthetic.main.activity_edit_task.*
import ru.andrroider.apps.data.db.Plans
import ru.andrroider.apps.mindcard.EDIT_TASK_ID
import ru.andrroider.apps.mindcard.PLAN_ID
import ru.andrroider.apps.mindcard.R
import ru.andrroider.apps.mindcard.base.BaseMvpActivity
import ru.andrroider.apps.mindcard.di.AppComponentInjector
import ru.andrroider.apps.mindcard.extentions.getDarkerColor
import ru.andrroider.apps.mindcard.extentions.setAfterTextChangedAction
import ru.andrroider.apps.mindcard.plans.creation.EditPlanPresenter
import ru.andrroider.apps.mindcard.plans.creation.NewPlanView
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Jackson on 27/03/2018.
 */

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
                .setPositiveButton(R.string.yes) { _, _ ->
                    saveWithBlankCheck()
                }
                .setNegativeButton(R.string.no) { _, _ ->
                    finish()
                }
                .create()
    }
    private val planId by lazy { intent.getLongExtra(PLAN_ID, -1) }
    private val taskId by lazy { intent.getLongExtra(EDIT_TASK_ID, -1) }
    private val colorPickerBuilder = ColorPickerDialog.newBuilder()
    private var preselectedColor: Int = ContextCompat.getColor(AppComponentInjector.component().appContext(), R.color.md_green_500)
    private val calendarStart = Calendar.getInstance()
    private val calendarEnd = Calendar.getInstance()

    lateinit var dateFromListener: DatePickerDialog.OnDateSetListener
    lateinit var dateDueListener: DatePickerDialog.OnDateSetListener
    lateinit var timeFromListener: TimePickerDialog.OnTimeSetListener
    lateinit var timeDueListener: TimePickerDialog.OnTimeSetListener

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
        setupDateDialogPickers()
        setupTimeDialogPickers()
        initDateDialogListeners(calendarStart, calendarEnd)
    }

    private fun saveWithBlankCheck() {
        when {
            taskTitle.text.isNullOrBlank() -> taskTitleContainer.error = getString(R.string.title_error)
            taskId > -1 -> {
                presenter.updateItem(
                        taskTitle.text.toString(),
                        taskDescription.text.toString(),
                        taskId,
                        taskColor.backgroundTintList!!.defaultColor,
                        planId)
            }
            else -> {
                val planId = intent.getLongExtra(PLAN_ID, -1)
                val plan = Plans(planId = planId,
                                 title = taskTitle.text.toString(),
                                 description = taskDescription.text.toString(),
                                 colorInt = taskColor.backgroundTintList!!.defaultColor,
                                 fromTimestamp = calendarStart.timeInMillis,
                                 toTimestamp = calendarEnd.timeInMillis)

                presenter.addNewItem(plan)
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
                .setDialogId(101)
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

    fun setupDateDialogPickers() {

        dateFromListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            calendarStart.set(Calendar.YEAR, year)
            calendarStart.set(Calendar.MONTH, monthOfYear)
            calendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateText()
        }

        dateDueListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            calendarEnd.set(Calendar.YEAR, year)
            calendarEnd.set(Calendar.MONTH, monthOfYear)
            calendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateDateText()
        }
    }

    fun setupTimeDialogPickers() {
        timeFromListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            calendarStart.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendarStart.set(Calendar.MINUTE, minute)
            updateTimeText()
        }

        timeDueListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            calendarEnd.set(Calendar.HOUR_OF_DAY, hourOfDay)
            calendarEnd.set(Calendar.MINUTE, minute)
            updateTimeText()
        }
    }

    fun initDateDialogListeners(start: Calendar, end: Calendar) {
        dateFrom.setOnClickListener {
            val datePickerDialogF = DatePickerDialog(dateFrom.context, dateFromListener, start.get(Calendar.YEAR),
                                                     start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH))
            datePickerDialogF.datePicker.minDate = System.currentTimeMillis() - 1000
            datePickerDialogF.setCanceledOnTouchOutside(true)
            datePickerDialogF.show()
        }

        dateDue.setOnClickListener {
            val datePickerDialogD = DatePickerDialog(dateDue.context, dateDueListener, end.get(Calendar.YEAR),
                                                     end.get(Calendar.MONTH), end.get(Calendar.DAY_OF_MONTH))
            datePickerDialogD.datePicker.minDate = System.currentTimeMillis() - 1000
            datePickerDialogD.setCanceledOnTouchOutside(true)
            datePickerDialogD.show()
        }

        timeFrom.setOnClickListener {
            val timePickerDialogF = TimePickerDialog(timeFrom.context, timeFromListener, start.get(Calendar.HOUR_OF_DAY), start.get(Calendar.MINUTE), DateFormat.is24HourFormat(timeFrom.context))
            timePickerDialogF.setCanceledOnTouchOutside(true)
            timePickerDialogF.show()
        }

        timeDue.setOnClickListener {
            val timePickerDialogD = TimePickerDialog(timeDue.context, timeDueListener, end.get(Calendar.HOUR_OF_DAY), end.get(Calendar.MINUTE), DateFormat.is24HourFormat(timeDue.context))
            timePickerDialogD.setCanceledOnTouchOutside(true)
            timePickerDialogD.show()
        }
    }

    private fun updateDateText() {
        val format = "dd/MM/yyyy"
        val simpleDateFormat = SimpleDateFormat(format, Locale.ROOT)

        dateFrom.text = simpleDateFormat.format(calendarStart.time)
        dateDue.text = simpleDateFormat.format(calendarEnd.time)
    }

    private fun updateTimeText() {
        val format = "HH:mm"
        val simpleDateFormat = SimpleDateFormat(format, Locale.ROOT)

        timeFrom.text = simpleDateFormat.format(calendarStart.time)

//        if (switcher) {
        calendarEnd.set(Calendar.HOUR_OF_DAY, calendarStart.get(Calendar.HOUR_OF_DAY) + 1)
        calendarEnd.set(Calendar.MINUTE, calendarStart.get(Calendar.MINUTE))
        updateDateText()
//        }

        timeDue.text = simpleDateFormat.format(calendarEnd.time)
    }

    private fun updateDateText(calendarStart: Calendar, calendarEnd: Calendar) {
        val format = "dd/MM/yyyy"
        val simpleDateFormat = SimpleDateFormat(format, Locale.ROOT)

        dateFrom.text = simpleDateFormat.format(calendarStart.time)
        dateDue.text = simpleDateFormat.format(calendarEnd.time)
    }

    private fun updateTimeText(calendarStart: Calendar, calendarEnd: Calendar) {
        val format = "HH:mm"
        val simpleDateFormat = SimpleDateFormat(format, Locale.ROOT)

        timeFrom.text = simpleDateFormat.format(calendarStart.time)

//        if (switcher) {
        calendarEnd.set(Calendar.HOUR_OF_DAY, this.calendarStart.get(Calendar.HOUR_OF_DAY) + 1)
        calendarEnd.set(Calendar.MINUTE, this.calendarStart.get(Calendar.MINUTE))
        updateDateText()
//        }

        timeDue.text = simpleDateFormat.format(calendarEnd.time)
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