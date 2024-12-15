package com.example.myapplication.utils

import android.content.Context
import android.content.SharedPreferences

object SharedPrefs {
    private const val PREF_NAME = "app_preferences"
    private const val TOKEN_KEY = "token_key"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(context: Context, token: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(TOKEN_KEY, token)
        editor.apply()
    }

    fun getToken(context: Context): String? {
        return getSharedPreferences(context).getString(TOKEN_KEY, null)
    }

    fun clearToken(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.remove(TOKEN_KEY)
        editor.apply()
    }
}
