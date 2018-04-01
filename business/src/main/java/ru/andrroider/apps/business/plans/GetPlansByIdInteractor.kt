package ru.andrroider.apps.business.plans

import io.reactivex.Flowable
import ru.andrroider.apps.data.db.Plans
import ru.andrroider.apps.data.db.PlansDao

class GetPlansByIdInteractor(private val plansDao: PlansDao) : (Long) -> Flowable<Plans> {
    override fun invoke(itemId: Long): Flowable<Plans> {
        return plansDao.getPlanById(itemId)
    }
}