package ru.andrroider.apps.business.plans.schedule

import io.reactivex.Flowable
import ru.andrroider.apps.data.db.Plans
import ru.andrroider.apps.data.db.PlansDao

class GetAllScheduledTasks(private val plansDao: PlansDao) : () -> Flowable<List<Plans>> {
    override fun invoke(): Flowable<List<Plans>> {
        return plansDao.getScheduledTasks()
    }
}