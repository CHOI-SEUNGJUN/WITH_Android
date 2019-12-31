package com.with.app.manage

import android.content.Context
import androidx.core.content.edit
import org.koin.dsl.module

class RegionManager(context: Context) {
    private val preferences = context.getSharedPreferences(
        REGION_PREFERENCES,
        Context.MODE_PRIVATE
    )

    var code: String
        get() {
            return preferences.getString(REGION_CODE, null).orEmpty()
        }
        set(value) {
            preferences.edit {
                putString(REGION_CODE, value)
            }
        }

    var name: String
        get() {
            return preferences.getString(REGION_NAME, null).orEmpty()
        }
        set(value) {
            preferences.edit{
                putString(REGION_NAME, value)
            }
        }

    private companion object {
        const val REGION_PREFERENCES = "region"
        const val REGION_CODE = "code"
        const val REGION_NAME = "name"
    }
}

val regionModule = module {
    single { RegionManager(get()) }
}