package com.with.app.util

import android.content.Context
import android.widget.Toast

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun Context.setStr(context: Context, key: String, value: String) {
    val prefs = context.getSharedPreferences("", Context.MODE_PRIVATE)
    val editor = prefs!!.edit()
    editor.putString(key, value).apply()
}
