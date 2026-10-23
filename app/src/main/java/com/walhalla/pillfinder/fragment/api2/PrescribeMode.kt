package com.walhalla.pillfinder.fragment.api2

import android.content.Context

/**
 * Roadmap #12 / section 5: "Prescribable only (Rx)" switch of Drug Search, persisted in SharedPreferences.
 *
 * ON  -> the tabs that have a /Prescribe twin use the generated prescribe* operations
 *        (findRxcui, approximateTerm, spellingsuggestions, allrelated, related, termtypes, relatypes, allProperties),
 *        so only concepts of the RxNorm Current Prescribable Content (sources RXNORM, MTHSPL, MTHCMSFRF) appear.
 * OFF -> the standard operations, behaviour unchanged.
 * NDC operations have no Prescribe twin and are never switched (roadmap section 5 / section 6 item 9).
 *
 * [enabled] is a cached value for callers without a Context (RxNormLib); warm it with [isEnabled] (RxNorm.onCreate).
 */
object PrescribeMode {
    private const val PREFS = "rxnorm_prescribe_mode"
    private const val KEY_ENABLED = "prescribable_only"

    @Volatile
    private var cached: Boolean? = null

    /** Last known state without touching SharedPreferences (false until [isEnabled] / [set] was called once). */
    @JvmStatic
    val enabled: Boolean
        get() = cached ?: false

    @JvmStatic
    fun isEnabled(context: Context): Boolean {
        cached?.let { return it }
        val v = context.applicationContext
            .getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getBoolean(KEY_ENABLED, false)
        cached = v
        return v
    }

    @JvmStatic
    fun set(context: Context, value: Boolean) {
        cached = value
        context.applicationContext
            .getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putBoolean(KEY_ENABLED, value).apply()
    }
}
