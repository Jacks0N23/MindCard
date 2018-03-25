package ru.andrroider.apps.mindcard.widget.recyclerView

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import ru.andrroider.apps.data.ViewTyped

/**
 * Created by Jackson on 03/02/2018.
 */
open class BaseViewHolder<in T>(view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(item: T) {}
}

typealias HolderFactory = (ViewGroup, Int) -> BaseViewHolder<ViewTyped>