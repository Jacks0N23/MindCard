package ru.andrroider.apps.mindcard.plans

import ru.andrroider.apps.business.plans.PlanUi
import ru.andrroider.apps.data.db.Plans
import ru.andrroider.apps.mindcard.R

/**
 * Created by Jackson on 05/02/2018.
 */
class PlansToPlanUI : (List<Plans>) -> List<PlanUi> {
    override fun invoke(plans: List<Plans>): List<PlanUi> {
        return plans.map { PlanUi(it.id, it.title, it.description, R.layout.item_plan) }
    }
}