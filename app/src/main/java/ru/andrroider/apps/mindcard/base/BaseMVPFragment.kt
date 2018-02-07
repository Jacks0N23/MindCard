package ru.andrroider.apps.mindcard.base

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import kotlinx.android.synthetic.main.fragment_plans.*

/**
 * Created by Jackson on 03/02/2018.
 */
abstract class BaseMVPFragment : MvpAppCompatFragment() {
    abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        return inflater.inflate(layoutId, container, false)
    }

    fun showErrorWithSnackbar(throwable: Throwable) {
        Snackbar.make(container, "Возникла ошибка", Snackbar.LENGTH_INDEFINITE)
        throwable.printStackTrace()
    }
}