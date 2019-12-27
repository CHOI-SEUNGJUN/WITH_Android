package com.with.app

import android.app.Application
import com.with.app.auth.authModule
import com.with.app.auth.requestModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WithApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WithApplication)
            modules(listOf(
                authModule,
                requestModule
            ))
        }
    }
}