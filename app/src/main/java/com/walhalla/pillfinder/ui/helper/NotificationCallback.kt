package com.walhalla.pillfinder.ui.helper

import android.text.Spannable

interface NotificationCallback {
    fun setMainTitle(var1: String, var2: Spannable)

    fun mSnackbar(settings_reset: Int)
}
