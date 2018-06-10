package ru.andrroider.apps.mindcard.extentions

import android.graphics.Color

fun Int.getDarkerColor(): Int {
    val alpha = Color.alpha(this)
    val red = Color.red(this)
    val green = Color.green(this)
    val blue = Color.blue(this)
    return Color.argb(alpha, (red * 0.9).toInt(), (green * 0.9).toInt(), (blue * 0.9).toInt())
}