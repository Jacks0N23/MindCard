package ru.andrroider.apps.mindcard.plans

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_plans.*
import ru.andrroider.apps.business.plans.PlanUi
import ru.andrroider.apps.mindcard.R
import ru.andrroider.apps.mindcard.base.BaseMVPFragment
import ru.andrroider.apps.mindcard.di.AppComponentInjector
import ru.andrroider.apps.mindcard.plans.creation.startNewPlanActivity
import ru.andrroider.apps.mindcard.plans.di.PlansComponent

class PlansFragment : BaseMVPFragment(), PlansView {

    override val layoutId: Int = R.layout.fragment_plans
    private val component by lazy {
        PlansComponent(activity,
                       fragmentManager,
                       deleteCardAction = {
                           presenter.deletePlan(it)
                       },
                       editCardAction = {
                           activity?.let { activity -> startNewPlanActivity(activity, it) }
                       })
    }
    private val adapter by lazy { component.adapter }
    @InjectPresenter
    lateinit var presenter: PlansPresenter

    @ProvidePresenter
    fun providePresenter(): PlansPresenter = AppComponentInjector.component().planPresenter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        plansList.adapter = adapter
        presenter.loadAllPlans()
        fab.setOnClickListener { activity?.let { startNewPlanActivity(it) } }
    }

    override fun planSuccessfullyDeleted(indexOfDeletedItem: Int) {
        adapter.removeItem(indexOfDeletedItem)
    }

    override fun showPlans(plans: List<PlanUi>) {
        emptyList.visibility = View.GONE
        plansList.visibility = View.VISIBLE

        adapter.setItems(plans)
        adapter.notifyDataSetChanged()
    }

    override fun showError(throwable: Throwable) {
        showErrorWithSnackbar(throwable)
    }

    override fun showEmpty() {
        emptyList.visibility = View.VISIBLE
        plansList.visibility = View.GONE
    }
}