package ru.andrroider.apps.data

/**
 * Created by Jackson on 03/02/2018.
 */
interface ComponentInjector<T> {
    fun component(): T
    fun setComponent(component: T)
    fun clear()
}

class SynchronizedComponentInjectorImpl<T> : ComponentInjector<T> {
    private var component: T? = null

    @Synchronized override fun component(): T = component ?: throw IllegalStateException("call when userComponent null")

    @Synchronized override fun setComponent(component: T) {
        this.component = component
    }

    @Synchronized override fun clear() {
        component = null
    }
}