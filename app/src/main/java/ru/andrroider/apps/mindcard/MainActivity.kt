package ru.andrroider.apps.mindcard

import android.os.Bundle
import ru.andrroider.apps.mindcard.base.BaseMvpActivity

const val PLAN_TITLE = "PLAN_TITLE"
const val PLAN_ID = "PLAN_ID"
const val EDIT_TASK_ID = "EDIT_TASK_ID"

class MainActivity : BaseMvpActivity(R.layout.activity_plans) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
                .replace(R.id.base_container, NavigationFragment())
                .commit()
    }
}