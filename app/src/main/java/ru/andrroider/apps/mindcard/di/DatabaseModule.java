package ru.andrroider.apps.mindcard.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ru.andrroider.apps.data.db.PlansDao;
import ru.andrroider.apps.data.db.PlansDatabase;

/**
 * Created by Jackson on 03/02/2018.
 */

@Module
public class DatabaseModule {

    @Provides
    @ApplicationContext
    static PlansDatabase providePlansDatabase(Context context) {
        return Room.databaseBuilder(context, PlansDatabase.class, "plans-db").build();
    }

    @Provides
    @ApplicationContext
    static PlansDao providePlansDao(PlansDatabase db) {
        return db.plansDao();
    }
}
