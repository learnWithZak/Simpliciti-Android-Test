package com.zak.simpliciti_android_test

import android.app.Application
import com.zak.simpliciti_android_test.di.apiModule
import com.zak.simpliciti_android_test.di.repositoryModule
import com.zak.simpliciti_android_test.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@BaseApplication)
            modules(listOf(repositoryModule, retrofitModule, apiModule))
        }
    }
}