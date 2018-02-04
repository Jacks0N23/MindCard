package ru.andrroider.apps.mindcard.plans

import android.view.View
import android.widget.TextView
import ru.andrroider.apps.business.plans.PlanUi
import ru.andrroider.apps.mindcard.R
import ru.andrroider.apps.mindcard.extentions.findView
import ru.andrroider.apps.mindcard.widget.recyclerView.BaseViewHolder

/**
 * Created by Jackson on 03/02/2018.
 */
class PlanViewHolder(view: View) : BaseViewHolder<PlanUi>(view) {

    private val title: TextView = view.findView(R.id.planTitle)
    private val description: TextView = view.findView(R.id.planDescription)

    override fun bind(item: PlanUi) {
        title.text = item.title
        description.text = item.description
    }
}