package ru.andrroider.apps.mindcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.andrroider.apps.mindcard.base.BaseMVPFragment

class PlansActivityFragment : BaseMVPFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_plans, container, false)
    }
}
