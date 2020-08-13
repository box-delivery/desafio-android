package com.jlmcdeveloper.githubjavapop.di

import org.koin.dsl.module

val addPasswordModule = module {  }

val activityModules = listOf(
    repositoryModule, addPasswordModule)