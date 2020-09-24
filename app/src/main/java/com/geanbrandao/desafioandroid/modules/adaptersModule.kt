package com.geanbrandao.desafioandroid.modules

import com.geanbrandao.desafioandroid.ui.adapter.ReposAdapter
import org.koin.dsl.module

val adaptersModule = module {
    factory { ReposAdapter(get(), get()) }
}