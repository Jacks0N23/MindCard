package ru.andrroider.apps.mindcard.plans.tasks

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_tasks.*
import ru.andrroider.apps.business.plans.TaskUi
import ru.andrroider.apps.mindcard.R
import ru.andrroider.apps.mindcard.base.BaseMVPFragment
import ru.andrroider.apps.mindcard.di.AppComponentInjector
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
                      TasksView {

    override val layoutId: Int = R.layout.fragment_tasks
    private val component by lazy {
        TaskComponent(activity = activity, fragmentManager = fragmentManager, deleteCardAction = {
            presenter.deleteTask(it)
        }, editCardAction = {
            activity?.let { activity -> startNewTaskActivity(activity, planId, it) }
        })
    }
    private val adapter by lazy { component.adapter }
    private val planId by lazy { arguments?.getLong(PLAN_ID) }
    @InjectPresenter
    lateinit var presenter: TasksPresenter

    @ProvidePresenter
    fun providePresenter(): TasksPresenter = AppComponentInjector.component().tasksPresenter()

    override fun onStart() {
        super.onStart()
        toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
        activity?.title = arguments?.getString(PLAN_TITLE)

        tasksList.adapter = adapter

        presenter.loadAllTasksByPlanId(planId)
        fab.setOnClickListener { activity?.let { startNewTaskActivity(it, planId) } }
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
}
