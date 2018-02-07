package ru.andrroider.apps.mindcard.di;

/**
 * Created by Jackson on 03/02/2018.
 */

import dagger.Component;
import ru.andrroider.apps.mindcard.plans.PlansPresenter;
import ru.andrroider.apps.mindcard.plans.creation.NewPlanPresenter;

@ApplicationContext
@Component(modules = {AppModule.class, DatabaseModule.class, PlansModule.class})
public interface AppComponent {
    PlansPresenter planPresenter();

    NewPlanPresenter newPlanPresenter();
}
