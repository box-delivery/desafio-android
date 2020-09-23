package com.geanbrandao.desafioandroid

import android.app.Application
import com.geanbrandao.desafioandroid.modules.viewModelModule
import org.koin.android.BuildConfig
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            modules(listOf(viewModelModule))
        }
    }
}