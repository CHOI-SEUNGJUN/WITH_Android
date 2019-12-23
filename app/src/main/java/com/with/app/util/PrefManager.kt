package com.with.app.util

import android.content.Context

object PrefManager{
    val PREFS_FILENAME = "prefs"

    fun setStr(context : Context, key : String, value : String){
        val prefs = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        val editor = prefs!!.edit()
        editor.putString(key, value).apply()
    }

    fun getStr(context : Context, key : String) : String? {
        val prefs = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        return prefs.getString(key, null)
    }

    fun setBoolean(context : Context, key : String, value : Boolean){
        val prefs = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        val editor = prefs!!.edit()
        editor.putBoolean(key, value).apply()
    }

    fun getBoolean(context : Context, key : String) : Boolean? {
        val prefs = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(key, false)
    }

}