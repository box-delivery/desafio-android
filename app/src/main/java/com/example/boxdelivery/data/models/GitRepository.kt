package com.example.boxdelivery.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class GitRepository(
    @PrimaryKey val id: String,
    val repoName: String,
    val repoDescription: String,
    val forks: Int,
    val stars: Int
)