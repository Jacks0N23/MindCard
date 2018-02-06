package ru.andrroider.apps.mindcard.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment

/**
 * Created by Jackson on 03/02/2018.
 */
abstract class BaseMVPFragment : MvpAppCompatFragment() {
    abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        retainInstance = true
        return inflater.inflate(layoutId, container, false)
    }
}