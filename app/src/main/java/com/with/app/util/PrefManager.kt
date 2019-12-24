package com.with.app.util

import android.content.Context

val PREFS_FILENAME = "prefs"

fun Context.setPrefStr(key: String, value: String) {
    val prefs = this.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
    val editor = prefs!!.edit()
    editor.putString(key, value).apply()
}

fun Context.getPrefStr(key: String): String? {
    val prefs = this.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
    return prefs.getString(key, null)
}

fun Context.setPrefBoolean(key: String, value: Boolean) {
    val prefs = this.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
    val editor = prefs!!.edit()
    editor.putBoolean(key, value).apply()
}

fun Context.getPrefBoolean(key: String): Boolean? {
    val prefs = this.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
    return prefs.getBoolean(key, false)
}
