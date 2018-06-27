package ru.andrroider.apps.mindcard.scedule

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alamkanak.weekview.DateTimeInterpreter
import com.alamkanak.weekview.MonthLoader
import com.alamkanak.weekview.WeekViewEvent
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_schedule.*
import ru.andrroider.apps.mindcard.R
import ru.andrroider.apps.mindcard.di.AppComponentInjector
import ru.andrroider.apps.mindcard.extentions.asType
import ru.andrroider.apps.mindcard.extentions.inflate
import java.text.SimpleDateFormat
import java.util.*

class ScheduleFragment : MvpAppCompatFragment(), MonthLoader.MonthChangeListener, ScheduleView {
    private val weekViewEvent = ArrayList<WeekViewEvent>()

    @InjectPresenter
    lateinit var presenter: SchedulePresenter

    @ProvidePresenter
    fun providePresenter(): SchedulePresenter = AppComponentInjector.component().schedulePresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_schedule)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.loadAllScheduledTasks()
        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        weekView.monthChangeListener = this

        // Show a toast message about the touched event.
//        weekView.setOnEventClickListener(this)


        // Set long press listener for events.
//        weekView.eventLongPressListener = this

        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        setupDateTimeInterpreter(false)

    }

    override fun showEvents(events: List<WeekViewEvent>) {
        weekViewEvent.clear()
        weekViewEvent.addAll(events)
        weekView.notifyDatasetChanged()

    }

    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     * @param shortDate True if the date values should be short.
     */
    private fun setupDateTimeInterpreter(shortDate: Boolean) {
        weekView.dateTimeInterpreter = object : DateTimeInterpreter {
            override fun interpretTime(hour: Int, minutes: Int): String {
                return hour.toString() + "" //> 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }

            override fun interpretDate(date: Calendar): String {
                val weekdayNameFormat = SimpleDateFormat("EEE", Locale.getDefault())
                var weekday = weekdayNameFormat.format(date.time)
                val format = SimpleDateFormat(" d/M", Locale.getDefault())

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = weekday[0].toString()
                return weekday.toUpperCase() + format.format(date.time)
            }
        }
    }

    override fun onMonthChange(newYear: Int, newMonth: Int): List<WeekViewEvent> {
        val monthEvents = weekViewEvent.filter { it.eventMatches(newYear, newMonth) }
        monthEvents.forEach { it.color = ContextCompat.getColor(context.asType(), R.color.now_line) }
        return monthEvents

    }
}

fun WeekViewEvent.eventMatches(year: Int, month: Int): Boolean {
    return startTime.get(Calendar.YEAR) == year &&
            startTime.get(Calendar.MONTH) == month - 1
            || endTime.get(Calendar.YEAR) == year
            && endTime.get(Calendar.MONTH) == month - 1
}