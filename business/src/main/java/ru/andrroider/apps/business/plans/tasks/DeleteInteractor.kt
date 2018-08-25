package ru.andrroider.apps.business.plans.tasks

import io.reactivex.Completable
import ru.andrroider.apps.data.db.PlansDao

class DeleteInteractor(private val plansDao: PlansDao) : (Long) -> Completable {
    override fun invoke(deleteItemId: Long): Completable {
        return Completable.fromAction { plansDao.deleteById(deleteItemId) }
    }
}