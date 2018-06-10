package ru.andrroider.apps.mindcard.di;

/**
 * Created by Jackson on 03/02/2018.
 */

import javax.inject.Singleton;

import dagger.Component;
import ru.andrroider.apps.mindcard.plans.PlansPresenter;
import ru.andrroider.apps.mindcard.plans.creation.EditPlanPresenter;
import ru.andrroider.apps.mindcard.plans.di.PlansModule;
import ru.andrroider.apps.mindcard.plans.tasks.TasksPresenter;
import ru.andrroider.apps.mindcard.plans.tasks.di.TasksModule;

@ApplicationContext
@Singleton
@Component(modules = {AppModule.class, DatabaseModule.class, PlansModule.class, TasksModule.class})
public interface AppComponent {
    PlansPresenter planPresenter();

    EditPlanPresenter newPlanPresenter();

    TasksPresenter tasksPresenter();
}
