package ru.andrroider.apps.mindcard.plans

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_plans.*
import ru.andrroider.apps.business.plans.PlanUi
import ru.andrroider.apps.data.ViewTyped
import ru.andrroider.apps.mindcard.R
import ru.andrroider.apps.mindcard.base.BaseMVPFragment
import ru.andrroider.apps.mindcard.di.AppComponentInjector
import ru.andrroider.apps.mindcard.plans.creation.startNewPlanActivity
import ru.andrroider.apps.mindcard.widget.recyclerView.Adapter

class PlansFragment : BaseMVPFragment(), PlansView {
    override val layoutId: Int = R.layout.fragment_plans
    private val plansItems = mutableListOf<ViewTyped>()
    private val adapter = Adapter<PlanUi>(plansItems, holderFactory = PlansHolderFactory())

    @InjectPresenter
    lateinit var presenter: PlansPresenter

    @ProvidePresenter
    fun providePresenter(): PlansPresenter = AppComponentInjector.component().planPresenter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        plansList.adapter = adapter
        presenter.loadAllPlans()
        fab.setOnClickListener { activity?.let { startNewPlanActivity(it) } }
    }

    override fun showPlans(plans: List<PlanUi>) {
        plansItems.clear()
        plansItems.addAll(plans)
        adapter.notifyDataSetChanged()
    }

    override fun showError(throwable: Throwable) {
        showErrorWithSnackbar(throwable)
    }
}
