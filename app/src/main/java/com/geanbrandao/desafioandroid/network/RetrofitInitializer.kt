package com.geanbrandao.desafioandroid.network

import android.content.Context
import com.geanbrandao.desafioandroid.App
import com.geanbrandao.desafioandroid.isNetworkAvailable
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit


class RetrofitInitializer(context: Context) {

    companion object {
        const val BASE_URL = "https://api.github.com/"
        const val HEADER_CACHE_CONTROL = "Cache-Control"
        const val HEADER_PRAGMA = "Pragma"
    }

    private var gson = GsonBuilder().setLenient().create()

    private val cacheSize = (5 * 1024 * 1024).toLong()

    var retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient(context))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    /*
    fun createInterceptor(context: Context): ApiService {
        val okHttpClient = OkHttpClient.Builder()
                .cache(Cache(context.cacheDir, cacheSize))
                .addInterceptor { chain ->
                    var request = chain.request()

                    request = if (context.isOnline()) {
                        request.newBuilder().header("Cache-Control", "public, max-age=" + 10)
                                .removeHeader("Pragma").build()
                    } else {
                        request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                                .removeHeader("Pragma").build()
                    }

                    chain.proceed(request)
                }
                .build()

        retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()

        return retrofit.create(ApiService::class.java)
    }
    */

    fun createService(): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    private fun okHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
                .cache(Cache(context.cacheDir, cacheSize))
                .addInterceptor(httpLoggingInterceptor()) // used if network off OR on
                .addNetworkInterceptor(networkInterceptor()) // only used when network is on
                .addInterceptor(offlineInterceptor(context))
                .build()
    }


    private fun offlineInterceptor(context: Context): Interceptor {
        return Interceptor { chain ->
            Timber.d("offline interceptor: called.")
            var request = chain.request()

            // prevent caching when network is on. For that we use the "networkInterceptor"
            if (!context.isNetworkAvailable()) {

                val cacheControl = CacheControl.Builder()
                        .maxStale(7, TimeUnit.DAYS)
                        .build()

                request = request.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .cacheControl(cacheControl)
                        .build()
            }

            return@Interceptor chain.proceed(request)
        }
    }

    private fun networkInterceptor(): Interceptor {
        return Interceptor { chain ->
            Timber.d("network interceptor: called.")
            val response: Response = chain.proceed(chain.request())

            val cacheControl = CacheControl.Builder()
                    .maxAge(5, TimeUnit.SECONDS)
                    .build()

            return@Interceptor response.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                    .build()
        }
    }

    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.d("log: http log: $message")
            }
        })
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return httpLoggingInterceptor
    }

}