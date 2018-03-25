package ru.andrroider.apps.business.plans

import io.reactivex.Flowable
import ru.andrroider.apps.data.db.Plans
import ru.andrroider.apps.data.db.PlansDao

/**
 * Created by Jackson on 25/03/2018.
 */
class GetTasksByPlanIdInteractor(private val plansDao: PlansDao,
                                 private val tasksToTaskUi: (List<Plans>) -> List<TaskUi>) :
        (Long?) -> Flowable<List<TaskUi>> {
    override fun invoke(planId: Long?): Flowable<List<TaskUi>> {
        return plansDao.getTasksByPlanId(planId).map(tasksToTaskUi)
    }
}