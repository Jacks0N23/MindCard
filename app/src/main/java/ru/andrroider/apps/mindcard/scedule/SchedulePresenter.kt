package ru.andrroider.apps.mindcard.scedule

import com.alamkanak.weekview.WeekViewEvent
import com.arellomobile.mvp.InjectViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.andrroider.apps.business.plans.schedule.GetAllScheduledTasks
import ru.andrroider.apps.mindcard.base.BaseMvpPresenter
import ru.andrroider.apps.mindcard.extentions.asType
import java.util.*

@InjectViewState
class SchedulePresenter(private val getAllScheduledTasks: GetAllScheduledTasks) : BaseMvpPresenter<ScheduleView>() {

    fun loadAllScheduledTasks() {
        addSubscription(getAllScheduledTasks().subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread()).map {
                    it.map {
                        val calendarStart = Calendar.getInstance().apply { timeInMillis = it.fromTimestamp.asType() }
                        val calendarEnd = Calendar.getInstance().apply { timeInMillis = it.toTimestamp.asType() }
                        WeekViewEvent(it.id.toString(), it.title, calendarStart, calendarEnd).apply {
                            color = it.colorInt
                        }
                    }
                }.subscribe(viewState::showEvents, { t: Throwable -> throw t }))
    }
}