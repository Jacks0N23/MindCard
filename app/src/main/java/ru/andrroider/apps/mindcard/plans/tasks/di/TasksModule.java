package ru.andrroider.apps.mindcard.plans.tasks.di;

import dagger.Module;
import dagger.Provides;
import ru.andrroider.apps.business.plans.tasks.DeleteInteractor;
import ru.andrroider.apps.business.plans.tasks.GetTasksByPlanIdInteractor;
import ru.andrroider.apps.data.db.PlansDao;
import ru.andrroider.apps.mindcard.di.ApplicationContext;
import ru.andrroider.apps.mindcard.plans.tasks.TasksPresenter;
import ru.andrroider.apps.mindcard.plans.tasks.TasksToTaskUI;

/**
 * Created by Jackson on 05/02/2018.
 */

@Module
public class TasksModule {

    @Provides
    @ApplicationContext
    static GetTasksByPlanIdInteractor provideGetTasksByPlanIdInteractor(PlansDao plansDao) {
        return new GetTasksByPlanIdInteractor(plansDao, plans -> new TasksToTaskUI().invoke(plans));
    }

    @Provides
    @ApplicationContext
    static TasksPresenter providePlansPresenter(GetTasksByPlanIdInteractor getTasksByPlanIdInteractor,
                                                DeleteInteractor deleteInteractor) {
        return new TasksPresenter(getTasksByPlanIdInteractor, deleteInteractor);
    }
}
