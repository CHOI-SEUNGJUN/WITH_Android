package com.with.app.util

import android.content.Context
import androidx.core.content.edit
import org.koin.dsl.module

class PrefManager(context : Context) {

    private val preferences = context.getSharedPreferences(DATE_PREFERENCES, Context.MODE_PRIVATE)

    var startDate : String
        get() {
            return preferences.getString(START_DATE, "2019.12.25")!!
        }
        set(value) {
            preferences.edit {
                putString(START_DATE, value)
            }
        }

    var endDate : String
        get() {
            return preferences.getString(END_DATE, "2019.12.25")!!
        }
        set(value) {
            preferences.edit {
                putString(END_DATE, value)
            }
        }

    companion object {
        const val DATE_PREFERENCES = "date"
        const val START_DATE = "start"
        const val END_DATE = "end"
    }
}

val prefModule = module {
    single { PrefManager(get()) }
}