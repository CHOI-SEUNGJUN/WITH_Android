package com.with.app.extension

import android.content.Context
import android.widget.Toast

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.longToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}