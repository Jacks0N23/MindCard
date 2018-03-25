package ru.andrroider.apps.mindcard.plans.tasks

import android.os.Bundle
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_tasks.*
import ru.andrroider.apps.business.plans.TaskUi
import ru.andrroider.apps.data.ViewTyped
import ru.andrroider.apps.mindcard.R
import ru.andrroider.apps.mindcard.base.BaseMVPFragment
import ru.andrroider.apps.mindcard.di.AppComponentInjector
import ru.andrroider.apps.mindcard.extentions.asType
import ru.andrroider.apps.mindcard.extentions.findView
import ru.andrroider.apps.mindcard.plans.creation.PLAN_ID
import ru.andrroider.apps.mindcard.plans.creation.startNewPlanActivity
import ru.andrroider.apps.mindcard.widget.recyclerView.Adapter

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

class TasksFragment : BaseMVPFragment(), TasksView {

    override val layoutId: Int = R.layout.fragment_tasks
    private val tasksItems = mutableListOf<ViewTyped>()
    private val adapter = Adapter<TaskUi>(tasksItems, holderFactory = TasksHolderFactory({
        fragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainer,
                        newTasksInstance(it.tag.asType(), it.findView<TextView>(R.id.planTitle).text))
                ?.addToBackStack(null)
                ?.commit()
    }))

    @InjectPresenter
    lateinit var presenter: TasksPresenter

    @ProvidePresenter
    fun providePresenter(): TasksPresenter = AppComponentInjector.component().tasksPresenter()

    override fun onStart() {
        super.onStart()
        activity?.title = arguments?.getString(PLAN_TITLE)

        tasksList.adapter = adapter
        val planId = arguments?.getLong(PLAN_ID)
        presenter.loadAllTasksByPlanId(planId)
        fab.setOnClickListener { activity?.let { startNewPlanActivity(it, planId) } }
    }

    override fun showTasks(tasks: List<TaskUi>) {
        adapter.setItems(tasks)
    }

    override fun showError(throwable: Throwable) {
        showErrorWithSnackbar(throwable)
    }
}
