package com.walhalla.pillfinder.onboarding

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class OnboardingManager(private val context: Context) {

    private val preferences: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(
            context
        )

    val isOnbording: Boolean
        get() {
            val aa = preferences.getBoolean(
                KEY_ONBORDING,
                false
            ) //&& !BuildConfig.DEBUG
            return aa
        }

    fun isOnbording(b: Boolean) {
        preferences.edit().putBoolean(KEY_ONBORDING, b).apply()
    }

    companion object {
        private const val KEY_ONBORDING = "key_onbording00"
    }
}
