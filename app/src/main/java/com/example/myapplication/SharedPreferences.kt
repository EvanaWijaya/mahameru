package com.example.myapplication

import android.content.Context

object PreferencesHelper {
    private const val PREFS_NAME = "app_prefs"
    private const val ONBOARDING_COMPLETE = "onboarding_complete"

    fun isOnboardingComplete(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(ONBOARDING_COMPLETE, false)
    }

    fun setOnboardingComplete(context: Context, isComplete: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(ONBOARDING_COMPLETE, isComplete).apply()
    }
}


