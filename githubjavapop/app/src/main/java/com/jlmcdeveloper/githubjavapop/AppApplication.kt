package com.jlmcdeveloper.githubjavapop

import android.app.Application
import com.jlmcdeveloper.githubjavapop.di.activityModules
import com.jlmcdeveloper.githubjavapop.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        val listModules = activityModules + appModules

        startKoin {
            androidContext(this@AppApplication)
            modules(listModules)
        }
    }
}