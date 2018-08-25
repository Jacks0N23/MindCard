package ru.andrroider.apps.mindcard.base

import android.os.Bundle
import android.support.design.widget.Snackbar
import com.arellomobile.mvp.MvpAppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics
import kotlinx.android.synthetic.main.fragment_plans.*

/**
 * Created by Jackson on 03/02/2018.
 */
abstract class BaseMvpActivity(private val layoutId: Int) : MvpAppCompatActivity() {

    var onActivityBackPressed: OnActivityBackPressed? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        FirebaseAnalytics.getInstance(this)
    }

    fun showErrorWithSnackbar(throwable: Throwable) {
        Snackbar.make(container, "Возникла ошибка", Snackbar.LENGTH_INDEFINITE)
        throwable.printStackTrace()
    }

    override fun onBackPressed() {
        onActivityBackPressed?.onBackPressed()?.run {
            if (this) super.onBackPressed()
        } ?: super.onBackPressed()
    }
}