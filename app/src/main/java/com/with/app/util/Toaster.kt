package com.with.app.util

import android.content.Context
import android.widget.Toast

object Toaster {

    fun toast(context : Context, msg : String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun longToast(context : Context, msg : String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

}