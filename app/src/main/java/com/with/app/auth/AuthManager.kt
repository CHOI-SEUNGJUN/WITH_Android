package com.with.app.auth

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

    private companion object {
        const val AUTH_PREFERENCES = "auth"

        const val TOKEN_KEY = "token"
    }
}

val authModule = module {
    single { AuthManager(get()) }
}