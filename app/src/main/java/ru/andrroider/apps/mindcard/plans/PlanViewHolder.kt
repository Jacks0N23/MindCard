package ru.andrroider.apps.mindcard.plans

import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.item_plan.view.*
import ru.andrroider.apps.business.plans.PlanUi
import ru.andrroider.apps.mindcard.extentions.hide
import ru.andrroider.apps.mindcard.extentions.show
import ru.andrroider.apps.mindcard.widget.recyclerView.BaseViewHolder

/**
 * Created by Jackson on 03/02/2018.
 */
class PlanViewHolder(view: View) : BaseViewHolder<PlanUi>(view) {

    private val title: TextView = view.planTitle
    private val description: TextView = view.planDescription

    override fun bind(item: PlanUi) {
        title.text = item.title
        if (item.description.isNotBlank()) {
            description.show()
            description.text = item.description
        } else {
            description.hide()
        }
    }
}