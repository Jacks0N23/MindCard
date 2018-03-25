package ru.andrroider.apps.mindcard.plans

import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_plans.*
import ru.andrroider.apps.business.plans.PlanUi
import ru.andrroider.apps.data.ViewTyped
import ru.andrroider.apps.mindcard.R
import ru.andrroider.apps.mindcard.base.BaseMVPFragment
import ru.andrroider.apps.mindcard.di.AppComponentInjector
import ru.andrroider.apps.mindcard.extentions.asType
import ru.andrroider.apps.mindcard.extentions.findView
import ru.andrroider.apps.mindcard.plans.creation.startNewPlanActivity
import ru.andrroider.apps.mindcard.plans.tasks.newTasksInstance
import ru.andrroider.apps.mindcard.widget.recyclerView.Adapter

class PlansFragment : BaseMVPFragment(), PlansView {
    override val layoutId: Int = R.layout.fragment_plans
    private val plansItems = mutableListOf<ViewTyped>()
    private val adapter = Adapter<PlanUi>(plansItems, holderFactory = PlansHolderFactory({
        fragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainer,
                        newTasksInstance(it.tag.asType(), it.findView<TextView>(R.id.planTitle).text))
                ?.addToBackStack(null)
                ?.commit()
    }))

    @InjectPresenter
    lateinit var presenter: PlansPresenter

    @ProvidePresenter
    fun providePresenter(): PlansPresenter = AppComponentInjector.component().planPresenter()

    override fun onStart() {
        super.onStart()
        activity?.title = "MindCard"
        plansList.adapter = adapter
        presenter.loadAllPlans()
        fab.setOnClickListener { activity?.let { startNewPlanActivity(it, null) } }
    }

    override fun showPlans(plans: List<PlanUi>) {
        adapter.setItems(plans)
        adapter.notifyDataSetChanged()
    }

    override fun showError(throwable: Throwable) {
        showErrorWithSnackbar(throwable)
    }
}
