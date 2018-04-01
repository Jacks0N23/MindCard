package ru.andrroider.apps.mindcard.widget

import android.content.Context
import android.support.v7.app.AlertDialog
import ru.andrroider.apps.mindcard.R

fun Context.createDeleteConfirmationDialog(onPositiveButtonClicked: () -> Unit): AlertDialog {
    return AlertDialog.Builder(this)
        .setTitle(R.string.deleteWithQuestion)
        .setMessage(R.string.deleteMessage)
        .setPositiveButton(R.string.yes, { _, _ ->
            onPositiveButtonClicked()
        })
        .setNegativeButton(R.string.no, { dialog, _ ->
            dialog.cancel()
        })
        .create()
}