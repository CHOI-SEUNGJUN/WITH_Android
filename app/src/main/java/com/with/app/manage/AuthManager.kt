package com.with.app.manage

import android.content.Context
import androidx.core.content.edit
import org.koin.dsl.module

class AuthManager(context: Context) {
    private val preferences = context.getSharedPreferences(
        AUTH_PREFERENCES,
        Context.MODE_PRIVATE
    )

    var token: String
        get() {
            return preferences.getString(TOKEN_KEY, null).orEmpty()
        }
        set(value) {
            preferences.edit {
                putString(TOKEN_KEY, value)
            }
        }

    var idx: Int
        get() {
            return preferences.getInt(IDX_KEY, 0)
        }
        set(value) {
            preferences.edit {
                putInt(IDX_KEY, value)
            }
        }

    var name: String
        get() {
            return preferences.getString(NAME_KEY, null).orEmpty()
        }
        set(value) {
            preferences.edit {
                putString(NAME_KEY, value)
            }
        }

    var flag: Boolean
        get() {
            return preferences.getBoolean(FLAG_KEY, false)
        }
        set(value) {
            preferences.edit {
                putBoolean(FLAG_KEY, value)
            }
        }

    private companion object {
        const val AUTH_PREFERENCES = "auth"
        const val TOKEN_KEY = "token"
        const val IDX_KEY = "idx"
        const val NAME_KEY = "name"
        const val FLAG_KEY = "flag"
    }
}

val authModule = module {
    single { AuthManager(get()) }
}