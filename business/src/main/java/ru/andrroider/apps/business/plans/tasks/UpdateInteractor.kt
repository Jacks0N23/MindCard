package ru.andrroider.apps.business.plans.tasks

import io.reactivex.Completable
import ru.andrroider.apps.data.db.Plans
import ru.andrroider.apps.data.db.PlansDao

class UpdateInteractor(private val plansDao: PlansDao) : (Plans) -> Completable {
    override fun invoke(plan: Plans): Completable {
        return Completable.fromAction { plansDao.update(plan) }
    }
}