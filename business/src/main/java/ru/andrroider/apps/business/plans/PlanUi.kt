package ru.andrroider.apps.business.plans

import ru.andrroider.apps.data.ViewTyped

/**
 * Created by Jackson on 03/02/2018.
 */
data class PlanUi(val id: Long,
             val title: String,
             val description: String,
             override val viewType: Int) : ViewTyped