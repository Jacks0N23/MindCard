package ru.andrroider.apps.business.plans

import ru.andrroider.apps.data.ViewTyped

data class TaskUi(val id: Long,
                  val title: String,
                  val color: Int,
                  val description: String,
                  override val viewType: Int) : ViewTyped