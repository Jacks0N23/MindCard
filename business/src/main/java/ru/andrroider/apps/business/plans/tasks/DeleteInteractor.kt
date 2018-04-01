package ru.andrroider.apps.business.plans.tasks

import io.reactivex.Completable
import ru.andrroider.apps.data.db.PlansDao

class DeleteInteractor(private val plansDao: PlansDao) : (Long) -> Completable {
    override fun invoke(deleteItemId: Long): Completable {
        return Completable.defer {
            if (plansDao.deleteById(deleteItemId) == 0) {
                Completable.error(NoSuchElementException("incorrect id"))
            } else Completable.complete()
        }
    }
}