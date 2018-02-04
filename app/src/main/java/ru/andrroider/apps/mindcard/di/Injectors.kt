package ru.andrroider.apps.mindcard.di

import ru.andrroider.apps.data.ComponentInjector
import ru.andrroider.apps.data.SynchronizedComponentInjectorImpl

/**
 * Created by Jackson on 03/02/2018.
 */
object AppComponentInjector : ComponentInjector<AppComponent> by SynchronizedComponentInjectorImpl()