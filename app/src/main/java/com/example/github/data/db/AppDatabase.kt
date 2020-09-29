package com.example.github.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.github.models.GitRepositoriesSearchResult
import com.example.github.models.GitRepository
import com.example.github.models.PullRequest


@Database(
    entities = [
        GitRepository::class,
        GitRepositoriesSearchResult::class,
        PullRequest::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun gitRepositoryDao(): GitRepositoryDao

    abstract fun pullRequestDao(): PullRequestDao
}
