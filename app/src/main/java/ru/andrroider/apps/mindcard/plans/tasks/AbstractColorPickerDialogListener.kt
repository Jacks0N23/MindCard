package ru.andrroider.apps.mindcard.plans.tasks

import com.jaredrummler.android.colorpicker.ColorPickerDialogListener

abstract class AbstractColorPickerDialogListener : ColorPickerDialogListener {
    override fun onDialogDismissed(dialogId: Int) {
    }

    override fun onColorSelected(dialogId: Int, color: Int) {
    }
}