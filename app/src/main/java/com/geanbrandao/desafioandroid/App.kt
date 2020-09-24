package com.geanbrandao.desafioandroid

import android.app.Application
import android.net.ConnectivityManager
import com.geanbrandao.desafioandroid.modules.adaptersModule
import com.geanbrandao.desafioandroid.modules.viewModelModule
import io.reactivex.internal.functions.Functions
import io.reactivex.plugins.RxJavaPlugins
import okhttp3.Cache
import org.koin.core.context.startKoin
import timber.log.Timber


class App : Application() {

    private val cacheSize = (5 * 1024 * 1024).toLong()

    override fun onCreate() {
        super.onCreate()

        if (instance == null) {
            synchronized(App::class.java) {
                instance = App()
            }
        }

        RxJavaPlugins.setErrorHandler(Functions.emptyConsumer())


        Timber.plant(Timber.DebugTree())


        startKoin {
            modules(listOf(viewModelModule, adaptersModule))
        }
    }

    fun getInstance(): App {
        return instance ?: this
    }

//    fun hasNetwork(): Boolean {
//        return instance?.isNetworkConnected() ?: false
//    }
//
//    private fun isNetworkConnected(): Boolean {
//        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager?
//        val activeNetwork = cm?.activeNetworkInfo
//        return activeNetwork != null &&
//                activeNetwork.isConnectedOrConnecting
//    }

    companion object {
        private var instance: App? = null

        /**
         * @return [App] - instância da Classe, estática, ;
         */
        fun getInstance(): App {
            if (instance == null) {
                synchronized(App::class.java) {
                    instance = App()
                }
            }
            return instance!!
        }
    }
}