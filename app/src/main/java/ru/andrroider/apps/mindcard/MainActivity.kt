package ru.andrroider.apps.mindcard

import android.os.Bundle
import ru.andrroider.apps.mindcard.base.BaseMvpActivity
import ru.andrroider.apps.mindcard.plans.PlansFragment

class MainActivity : BaseMvpActivity(R.layout.activity_plans) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, PlansFragment())
                .commit()
    }
}