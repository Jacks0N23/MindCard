package ru.andrroider.apps.mindcard

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_plans.*
import ru.andrroider.apps.mindcard.base.BaseMvpActivity
import ru.andrroider.apps.mindcard.plans.PlansFragment

class MainActivity : BaseMvpActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plans)
        setSupportActionBar(toolbar)
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, PlansFragment())
                .commit()
    }
}
