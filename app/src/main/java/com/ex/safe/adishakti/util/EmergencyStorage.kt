package com.ex.safe.adishakti.util

import android.content.Context

object EmergencyStorage {

    private const val PREF_NAME = "adishakti_prefs"
    private const val KEY_PHONE = "emergency_phone"

    fun savePhone(context: Context, phone: String) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_PHONE, phone).apply()
    }

    fun getPhone(context: Context): String? {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_PHONE, null)
    }
}
