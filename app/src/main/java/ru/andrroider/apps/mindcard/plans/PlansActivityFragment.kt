package ru.andrroider.apps.mindcard.plans

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import kotlinx.android.synthetic.main.fragment_plans.*
import ru.andrroider.apps.business.plans.PlanUi
import ru.andrroider.apps.data.ViewTyped
import ru.andrroider.apps.mindcard.R
import ru.andrroider.apps.mindcard.base.BaseMVPFragment
import ru.andrroider.apps.mindcard.di.AppComponentInjector
import ru.andrroider.apps.mindcard.widget.recyclerView.Adapter

class PlansActivityFragment : BaseMVPFragment(), PlansView {

    override val layoutId: Int = R.layout.fragment_plans

    private val plansItems = mutableListOf<ViewTyped>()
    private val adapter = Adapter<PlanUi>(plansItems, holderFactory = PlansHolderFactory())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        plansList.adapter = adapter
        val presenter = AppComponentInjector.component().presenter()
        presenter.bindView(this)
        presenter.loadAllPlans()

        fab.setOnClickListener { _ ->
            presenter.addNewItem()
        }
    }

    override fun showPlans(plans: List<PlanUi>) {
        plansItems.clear()
        plansItems.addAll(plans)
        adapter.notifyDataSetChanged()
    }

    override fun showError(t: Throwable) {
        Snackbar.make(container, "Возникла ошибка", Snackbar.LENGTH_INDEFINITE)
        t.printStackTrace()
    }
}
