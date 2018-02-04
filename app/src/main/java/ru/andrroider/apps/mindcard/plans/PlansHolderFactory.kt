package ru.andrroider.apps.mindcard.plans

import android.view.ViewGroup
import android.widget.AdapterView
import ru.andrroider.apps.data.ViewTyped
import ru.andrroider.apps.mindcard.R
import ru.andrroider.apps.mindcard.extentions.inflate
import ru.andrroider.apps.mindcard.widget.recyclerView.BaseViewHolder
import ru.andrroider.apps.mindcard.widget.recyclerView.HolderFactory

/**
 * Created by Jackson on 03/02/2018.
 */
class PlansHolderFactory :HolderFactory {
    override fun invoke(viewGroup: ViewGroup, viewType: Int): BaseViewHolder<ViewTyped> {
        return when(viewType) {
            R.layout.item_plan -> {
                PlanViewHolder(viewGroup.inflate(viewType))
            }
            else -> throw IllegalStateException("unknown viewType")
        } as BaseViewHolder<ViewTyped>
    }

    override fun setOnItemClickListener(onItemClickListener: AdapterView.OnItemClickListener?) {
        //TODO
    }
}