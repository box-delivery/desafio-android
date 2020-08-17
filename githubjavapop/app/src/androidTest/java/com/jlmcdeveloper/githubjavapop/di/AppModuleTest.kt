package com.jlmcdeveloper.githubjavapop.di

import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// ------------------------ api --------------------------
val retrofitModuleTest = module {
    single {
        Retrofit.Builder()
            .baseUrl((get() as MockWebServer).url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .client(get() as OkHttpClient)
            .build()
    }
}

val mockWebServer = module {
    single { MockWebServer() }
}



val apiTest = listOf(mockWebServer, cacheModule, okHttpClientModule, retrofitModuleTest, apiModule)

val appModulesTest = apiTest + repositoryModule