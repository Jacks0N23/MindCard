package ru.andrroider.apps.mindcard.di;

import dagger.Module;
import dagger.Provides;
import ru.andrroider.apps.business.plans.AddPlanInteractor;
import ru.andrroider.apps.business.plans.GetPlansInteractor;
import ru.andrroider.apps.data.db.PlansDao;
import ru.andrroider.apps.mindcard.plans.PlansPresenter;
import ru.andrroider.apps.mindcard.plans.PlansToPlanUI;

/**
 * Created by Jackson on 05/02/2018.
 */

@Module
public class PlansModule {

    @Provides
    @ApplicationContext
    static GetPlansInteractor provideGetPlansInteractor(PlansDao plansDao) {
        return new GetPlansInteractor(plansDao, plans -> new PlansToPlanUI().invoke(plans));
    }

    @Provides
    @ApplicationContext
    static AddPlanInteractor provideAddPlanInteractor(PlansDao plansDao) {
        return new AddPlanInteractor(plansDao);
    }


    @Provides
    @ApplicationContext
    PlansPresenter providePlansPresenter(GetPlansInteractor getPlansInteractor,
                                         AddPlanInteractor addPlanInteractor) {
        return new PlansPresenter(getPlansInteractor, addPlanInteractor);
    }
}
