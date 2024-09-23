package com.ssafy.pickit.data.datasource.local.preference

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreference @Inject constructor(@ApplicationContext context: Context) {
    val preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    private val DEFAULT_STRING_VALUE = ""

    fun setValue(key: String, value: String) {
        preferences.edit {
            putString(key, value)
            apply()
        }
    }

    fun getValue(key: String): String? = preferences.getString(key, DEFAULT_STRING_VALUE)
}