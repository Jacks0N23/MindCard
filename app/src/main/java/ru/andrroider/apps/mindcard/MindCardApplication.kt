package ru.andrroider.apps.mindcard

import android.app.Application
import ru.andrroider.apps.mindcard.di.AppComponentInjector
import ru.andrroider.apps.mindcard.di.AppModule
import ru.andrroider.apps.mindcard.di.DaggerAppComponent

/**
 * Created by Jackson on 03/02/2018.
 */

class MindCardApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val component = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        AppComponentInjector.setComponent(component)
    }
}
