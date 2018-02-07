package ru.andrroider.apps.mindcard.extentions

import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

/**
 * Created by Jackson on 03/02/2018.
 */
fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layout, this, attachToRoot)
}

inline fun <T : View> View.findView(@IdRes id: Int): T = findViewById(
        id)

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun EditText.setAfterTextChangedAction(action: (Editable?)-> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(text: Editable?) {
            action(text)
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

    })
}