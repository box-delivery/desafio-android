package com.example.github.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.github.data.db.DbTypeConverters

@Entity(tableName = "repo_search_result")
@TypeConverters(DbTypeConverters::class)
data class GitRepositoriesSearchResult(
    @PrimaryKey val query: String,
    val gitRepositoryIds: List<Int>,
    val totalCount: Int,
    val next: Int?
)