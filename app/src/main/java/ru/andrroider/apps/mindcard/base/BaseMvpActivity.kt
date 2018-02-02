package ru.andrroider.apps.mindcard.base

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Created by Jackson on 03/02/2018.
 */
open class BaseMvpActivity : MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseAnalytics.getInstance(this)
    }
}