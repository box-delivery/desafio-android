package com.example.github.di

import android.content.Context
import androidx.room.Room
import com.example.github.AppExecutors
import com.example.github.data.api.GitService
import com.example.github.data.db.AppDatabase
import com.example.github.data.repo.GitRepositoryRepo
import com.example.github.data.repo.PullRequestsRepo
import com.example.github.util.LiveDataCallAdapterFactory
import com.example.github.util.ViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class GithubServiceInj

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class DatabaseInj

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class AppExecutorsInj

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class GitRepositoryRepoInj

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class GitRepositoryViewModelFactoryInj

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class PullRequestViewModelFactoryInj


    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class PullRequestRepoInj

    @Singleton
    @GithubServiceInj
    @Provides
    fun provideGithubService(): GitService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .build()
            .create(GitService::class.java)
    }

    @Singleton
    @DatabaseInj
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "Github.db"
        ).build()
    }

    @Singleton
    @AppExecutorsInj
    @Provides
    fun provideAppExecutors() : AppExecutors = AppExecutors()

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO


    @Singleton
    @GitRepositoryRepoInj
    @Provides
    fun providesGitRepositoryRepo(
        @AppExecutorsInj appExecutors: AppExecutors,
        @GithubServiceInj gitService: GitService,
        @DatabaseInj database: AppDatabase
    ): GitRepositoryRepo {
        return GitRepositoryRepo(appExecutors, gitService, database)
    }

    @Singleton
    @GitRepositoryViewModelFactoryInj
    @Provides
    fun providesRepositoryViewModelFactory(
        @GitRepositoryRepoInj gitRepositoryRepo: GitRepositoryRepo
    ): ViewModelFactory {
        return ViewModelFactory(gitRepositoryRepo)
    }


    @Singleton
    @PullRequestRepoInj
    @Provides
    fun providesPullRequestsRepo(
        @AppExecutorsInj appExecutors: AppExecutors,
        @GithubServiceInj gitService: GitService,
        @DatabaseInj database: AppDatabase
    ): PullRequestsRepo {
        return PullRequestsRepo(appExecutors, gitService, database)
    }

    @Singleton
    @PullRequestViewModelFactoryInj
    @Provides
    fun providesPullRequestViewModelFactory(
        @PullRequestRepoInj pullRequestsRepo: PullRequestsRepo
    ): ViewModelFactory {
        return ViewModelFactory(pullRequestsRepo)
    }
}