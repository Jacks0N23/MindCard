package ru.andrroider.apps.mindcard.scedule

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.andrroider.apps.mindcard.R
import ru.andrroider.apps.mindcard.extentions.inflate

class ScheduleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_schedule)
    }
}