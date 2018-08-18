package ru.andrroider.apps.mindcard

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import kotlinx.android.synthetic.main.fragment_navigation.*
import ru.andrroider.apps.mindcard.extentions.asType
import ru.andrroider.apps.mindcard.extentions.inflate
import ru.andrroider.apps.mindcard.plans.PlansFragment
import ru.andrroider.apps.mindcard.scedule.ScheduleFragment

class NavigationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_navigation)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupBottomBar()
    }

    private fun setupBottomBar() {
        val plans = AHBottomNavigationItem(getString(R.string.plans),
                ContextCompat.getDrawable(context.asType(), R.drawable.ic_plans_list))
        val schedule = AHBottomNavigationItem(getString(R.string.schedule),
                ContextCompat.getDrawable(context.asType(), R.drawable.ic_schedule))
        bottomNavigationView.apply {
            addItems(listOf(plans, schedule))
            defaultBackgroundColor = ContextCompat.getColor(context.asType(), R.color.colorPrimaryDark)
            // Change colors
            accentColor = ContextCompat.getColor(context.asType(), R.color.white)
            inactiveColor = ContextCompat.getColor(context.asType(), R.color.md_white_inactive)
            setOnTabSelectedListener { position, _ ->
                when (position) {
                    0 -> {
                        fragmentManager?.beginTransaction()
                                ?.replace(R.id.nav_container, PlansFragment())
                                ?.commit()
                        true
                    }
                    1 -> {
                        fragmentManager?.beginTransaction()
                                ?.replace(R.id.nav_container, ScheduleFragment())
                                ?.commit()
                        true
                    }
                    else -> {
                        throw IllegalStateException("unknown tab")
                    }
                }
            }
            titleState = AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE_FORCE
            currentItem = 0
        }
    }
}