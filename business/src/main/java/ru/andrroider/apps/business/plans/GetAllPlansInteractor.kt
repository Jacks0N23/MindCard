package ru.andrroider.apps.business.plans

import io.reactivex.Flowable
import ru.andrroider.apps.data.db.Plans
import ru.andrroider.apps.data.db.PlansDao

/**
 * Created by Jackson on 03/02/2018.
 */
class GetAllPlansInteractor(private val plansDao: PlansDao,
                            private val plansToPlansUi: (List<Plans>) -> List<PlanUi>) : () -> Flowable<List<PlanUi>> {
    override fun invoke(): Flowable<List<PlanUi>> {
        return plansDao.getAllPlans().map(plansToPlansUi)
    }
}