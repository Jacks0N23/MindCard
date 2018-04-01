package ru.andrroider.apps.mindcard.plans

import android.view.View
import android.view.ViewGroup
import ru.andrroider.apps.data.ViewTyped
import ru.andrroider.apps.mindcard.R
import ru.andrroider.apps.mindcard.extentions.asType
import ru.andrroider.apps.mindcard.extentions.inflate
import ru.andrroider.apps.mindcard.widget.recyclerView.BaseViewHolder
import ru.andrroider.apps.mindcard.widget.recyclerView.HolderFactory

/**
 * Created by Jackson on 03/02/2018.
 */
class PlansHolderFactory(private val onCardClickListener: (View) -> Unit,
                         private val onCardLongClickListener: (View) -> Boolean) : HolderFactory {

    override fun invoke(viewGroup: ViewGroup, viewType: Int): BaseViewHolder<ViewTyped> {
        return when (viewType) {
            R.layout.item_plan -> {
                PlanViewHolder(viewGroup.inflate(viewType), onCardClickListener, onCardLongClickListener)
            }
            else -> throw IllegalStateException("unknown viewType")
        }.asType()
    }
}