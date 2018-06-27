package ru.andrroider.apps.mindcard.plans.di;

import dagger.Module;
import dagger.Provides;
import ru.andrroider.apps.business.plans.AddPlanInteractor;
import ru.andrroider.apps.business.plans.GetAllPlansInteractor;
import ru.andrroider.apps.business.plans.GetPlansByIdInteractor;
import ru.andrroider.apps.business.plans.schedule.GetAllScheduledTasks;
import ru.andrroider.apps.business.plans.tasks.DeleteInteractor;
import ru.andrroider.apps.business.plans.tasks.UpdateInteractor;
import ru.andrroider.apps.data.db.PlansDao;
import ru.andrroider.apps.mindcard.di.ApplicationContext;
import ru.andrroider.apps.mindcard.plans.PlansPresenter;
import ru.andrroider.apps.mindcard.plans.PlansToPlanUI;
import ru.andrroider.apps.mindcard.plans.creation.EditPlanPresenter;
import ru.andrroider.apps.mindcard.scedule.SchedulePresenter;

/**
 * Created by Jackson on 05/02/2018.
 */

@Module
public class PlansModule {

    @Provides
    @ApplicationContext
    static GetAllPlansInteractor provideGetPlansInteractor(PlansDao plansDao) {
        return new GetAllPlansInteractor(plansDao, plans -> new PlansToPlanUI().invoke(plans));
    }

    @Provides
    @ApplicationContext
    static GetAllScheduledTasks provideGetAllScheduledTasks(PlansDao plansDao) {
        return new GetAllScheduledTasks(plansDao);
    }

    @Provides
    @ApplicationContext
    static GetPlansByIdInteractor provideGetPlansByIdInteractor(PlansDao plansDao) {
        return new GetPlansByIdInteractor(plansDao);
    }

    @Provides
    @ApplicationContext
    static AddPlanInteractor provideAddPlanInteractor(PlansDao plansDao) {
        return new AddPlanInteractor(plansDao);
    }

    @Provides
    @ApplicationContext
    static UpdateInteractor provideUpdateInteractor(PlansDao plansDao) {
        return new UpdateInteractor(plansDao);
    }

    @Provides
    @ApplicationContext
    static DeleteInteractor provideDeleteInteractor(PlansDao plansDao) {
        return new DeleteInteractor(plansDao);
    }

    @Provides
    @ApplicationContext
    static PlansPresenter providePlansPresenter(GetAllPlansInteractor getAllPlansInteractor,
                                                DeleteInteractor deleteInteractor) {
        return new PlansPresenter(getAllPlansInteractor, deleteInteractor);
    }

    @Provides
    @ApplicationContext
    static SchedulePresenter provideSchedulePresenter(GetAllScheduledTasks getAllScheduledTasks) {
        return new SchedulePresenter(getAllScheduledTasks);
    }

    @Provides
    EditPlanPresenter provideNewPlanPresenter(AddPlanInteractor addPlanInteractor,
                                              GetPlansByIdInteractor getPlansByIdInteractor,
                                              UpdateInteractor updateInteractor) {
        return new EditPlanPresenter(addPlanInteractor, getPlansByIdInteractor, updateInteractor);
    }
}
