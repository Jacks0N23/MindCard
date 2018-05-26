package ru.andrroider.apps.mindcard.plans.tasks

import android.content.Context
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_tasks.*
import ru.andrroider.apps.business.plans.TaskUi
import ru.andrroider.apps.mindcard.R
import ru.andrroider.apps.mindcard.base.BaseMVPFragment
import ru.andrroider.apps.mindcard.base.BaseMvpActivity
import ru.andrroider.apps.mindcard.base.OnActivityBackPressed
import ru.andrroider.apps.mindcard.di.AppComponentInjector
import ru.andrroider.apps.mindcard.extentions.asType
import ru.andrroider.apps.mindcard.plans.tasks.di.TaskComponent

/**
 * Created by Jackson on 25/03/2018.
 */
const val PLAN_TITLE = "PLAN_TITLE"

fun newTasksInstance(planId: Long, title: CharSequence): TasksFragment {
    return TasksFragment().apply {
        arguments = Bundle().apply {
            putString(PLAN_TITLE, title.toString())
            putLong(PLAN_ID, planId)
        }
    }
}

class TasksFragment : BaseMVPFragment(),
        TasksView, OnActivityBackPressed {

    override val layoutId: Int = R.layout.fragment_tasks
    private val component by lazy {
        TaskComponent(activity = activity, tasksPresenter = presenter, deleteCardAction = {
            presenter.deleteTask(it)
        }, editCardAction = {
            activity?.let { activity -> startNewTaskActivity(activity, planId, it) }
        })
    }
    private val adapter by lazy { component.adapter }
    private var planId = 0L
    @InjectPresenter
    lateinit var presenter: TasksPresenter

    @ProvidePresenter
    fun providePresenter(): TasksPresenter = AppComponentInjector.component().tasksPresenter()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        context.asType<BaseMvpActivity>().onActivityBackPressed = this
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
        tasksList.adapter = adapter
        fab.setOnClickListener { activity?.let { startNewTaskActivity(it, planId) } }
        presenter.addPlanToNodeAndLoadData(arguments?.getString(PLAN_TITLE).asType(),
                arguments?.getLong(PLAN_ID).asType())
    }

    override fun reloadData(title: String, planId: Long) {
        toolbar.title = title
        this.planId = planId
    }

    override fun showTasks(tasks: List<TaskUi>) {
        adapter.setItems(tasks)
    }

    override fun showError(throwable: Throwable) {
        showErrorWithSnackbar(throwable)
    }

    override fun taskSuccessfullyDeleted(indexOfDeletedItem: Int) {
        adapter.removeItem(indexOfDeletedItem)
    }

    override fun onBackPressed(): Boolean {
        return presenter.popPlanAndReloadData()
    }
}
