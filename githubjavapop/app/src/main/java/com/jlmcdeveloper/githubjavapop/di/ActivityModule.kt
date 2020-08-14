package com.jlmcdeveloper.githubjavapop.di

import com.jlmcdeveloper.githubjavapop.data.Repository
import com.jlmcdeveloper.githubjavapop.ui.main.MainViewModel
import com.jlmcdeveloper.githubjavapop.ui.pullrequest.PullRequestViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module { viewModel { MainViewModel(get() as Repository) } }

val pullRequestModule = module { viewModel { PullRequestViewModel(get() as Repository) } }

val activityModules = listOf(mainModule, pullRequestModule)