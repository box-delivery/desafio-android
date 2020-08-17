package com.jlmcdeveloper.githubjavapop.di

import com.jlmcdeveloper.githubjavapop.data.Repository
import com.jlmcdeveloper.githubjavapop.data.api.ApiEndPoint
import com.jlmcdeveloper.githubjavapop.data.api.ApiRestGithub
import com.jlmcdeveloper.githubjavapop.data.api.AppGithubDataSource
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// ------------------------ api --------------------------
val retrofitModule = module {
    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ApiEndPoint.url)
            .client(get() as OkHttpClient)
            .build()
    }
}
val cacheModule = module {
    val cacheSize: Long = 10 * 1024 * 1024
    single { Cache(androidContext().cacheDir, cacheSize) }
}

val okHttpClientModule = module {
    single { OkHttpClient.Builder().cache(get()).build() }
}

val apiModule = module {
    single { (get() as Retrofit).create(ApiRestGithub::class.java) }
    single { AppGithubDataSource(get()) }
}

val api = listOf(cacheModule, okHttpClientModule, retrofitModule, apiModule)



// ------------------------ repository --------------------------
val repositoryModule = module {
    single{ Repository(get() as AppGithubDataSource) }
}



val appModules = repositoryModule + api