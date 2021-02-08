package com.surkhojb

import android.app.Application
import com.surkhojb.ktwallpapers.di.dataModule
import com.surkhojb.ktwallpapers.di.networkModule
import com.surkhojb.ktwallpapers.di.viewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class KTApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@KTApp)
            modules(networkModule, dataModule, viewModelModules)
        }
    }
}