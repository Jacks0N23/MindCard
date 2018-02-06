package ru.andrroider.apps.mindcard.plans.creation

import android.os.Bundle
import ru.andrroider.apps.mindcard.MainActivity
import ru.andrroider.apps.mindcard.R
import ru.andrroider.apps.mindcard.base.BaseMVPFragment

/**
 * Created by Jackson on 07/02/2018.
 */
class NewPlanFragment : BaseMVPFragment() {
    override val layoutId: Int = R.layout.fragment_new_plan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as? MainActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as? MainActivity)?.supportActionBar?.setHomeButtonEnabled(true)
    }
}