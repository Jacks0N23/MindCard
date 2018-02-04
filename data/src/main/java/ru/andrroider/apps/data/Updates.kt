package ru.andrroider.apps.data

/**
 * Created by Jackson on 03/02/2018.
 */
sealed class Updates<out T>(val position: Int, val updatedItems: List<T>) {
    abstract fun <R> copy(mapper: (List<T>) -> (List<R>)): Updates<R>
    override fun toString(): String {
        return "type{$javaClass, position-$position, count-${updatedItems.size}, updatedItems-$updatedItems}"
    }
}

class Inserted<out T>(position: Int, items: List<T>, val toEnd: Boolean = false) : Updates<T>(position, items) {
    override fun <R> copy(mapper: (List<T>) -> List<R>): Updates<R> {
        return Inserted(position, mapper(updatedItems), toEnd)
    }
}

class Moved<out T>(val oldPosition: Int, position: Int, items: List<T>) : Updates<T>(position, items) {
    override fun <R> copy(mapper: (List<T>) -> List<R>): Updates<R> {
        return Moved(oldPosition, position, mapper(updatedItems))
    }

    override fun toString(): String {
        return super.toString() + ", oldposition-$oldPosition"
    }
}

class Add<out T>(position: Int, items: List<T>) : Updates<T>(position, items) {
    override fun <R> copy(mapper: (List<T>) -> List<R>): Updates<R> {
        return Add(position, mapper(updatedItems))
    }
}