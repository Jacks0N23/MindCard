package ru.andrroider.apps.mindcard.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public final class AppModule {

    final Context context;

    public AppModule(final Context context) {
        this.context = context;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return context;
    }
}