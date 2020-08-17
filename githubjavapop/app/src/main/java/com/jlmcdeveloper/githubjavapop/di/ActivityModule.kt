package com.jlmcdeveloper.githubjavapop.di

import com.jlmcdeveloper.githubjavapop.data.RepositoryMain
import com.jlmcdeveloper.githubjavapop.data.RepositoryPullRequest
import com.jlmcdeveloper.githubjavapop.ui.main.MainViewModel
import com.jlmcdeveloper.githubjavapop.ui.pullrequest.PullRequestViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module { viewModel { MainViewModel(get() as RepositoryMain) } }

val pullRequestModule = module { viewModel { PullRequestViewModel(get() as RepositoryPullRequest) } }

val activityModules = listOf(mainModule, pullRequestModule)