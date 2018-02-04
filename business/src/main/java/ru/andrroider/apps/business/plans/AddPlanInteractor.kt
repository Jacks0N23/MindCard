package ru.andrroider.apps.business.plans

import io.reactivex.Completable
import ru.andrroider.apps.data.db.Plans
import ru.andrroider.apps.data.db.PlansDao

/**
 * Created by Jackson on 05/02/2018.
 */
class AddPlanInteractor(private val plansDao: PlansDao) : (Plans) -> Completable {
    override fun invoke(plan: Plans): Completable {
        return Completable.fromAction { plansDao.insert(plan) }
    }
}