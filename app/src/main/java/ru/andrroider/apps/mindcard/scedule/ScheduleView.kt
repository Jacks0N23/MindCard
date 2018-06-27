package ru.andrroider.apps.mindcard.scedule

import com.alamkanak.weekview.WeekViewEvent
import com.arellomobile.mvp.MvpView

interface ScheduleView : MvpView {
    fun showEvents(events: List<WeekViewEvent>)
}