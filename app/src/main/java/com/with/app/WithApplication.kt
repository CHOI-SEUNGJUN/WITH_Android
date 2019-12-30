package com.with.app

import android.app.Application
import com.with.app.manage.authModule
import com.with.app.manage.requestModule
import com.with.app.manage.prefModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WithApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WithApplication)
            modules(listOf(
                authModule,
                requestModule,
                prefModule
            ))
        }
    }
}