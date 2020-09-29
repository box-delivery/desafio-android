package com.example.github.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "repositories")
data class GitRepository(
    @PrimaryKey val id: Int,

    @field:SerializedName("name")
    val repoName: String,

    @field:SerializedName("description")
    val repoDescription: String,

    @field:SerializedName("forks_count")
    val forks: Int,

    @field:SerializedName("stargazers_count")
    val stars: Int,

    @field:SerializedName("pulls_url")
    val pullsUrl: String,

    @field:SerializedName("owner")
    @field:Embedded(prefix = "owner_")
    val owner: Owner
) {
    data class Owner(
        @field:SerializedName("login")
        val login: String,
        @field:SerializedName("url")
        val url: String?,
        @field:SerializedName("avatar_url")
        val avatarUrl: String
    )
}