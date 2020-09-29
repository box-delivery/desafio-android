package com.example.github.models

import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "pull_requests",
    primaryKeys = [
        "id"
    ]
)
data class PullRequest(
    @field:SerializedName("node_id")
    var id: String,
    @field:SerializedName("title")
    var title: String,
    @field:SerializedName("body")
    var body: String,
    @field:SerializedName("html_url")
    var url: String,
    @field:SerializedName("user")
    @field:Embedded(prefix = "user_")
    var user: User,
    var repo_name: String
) {


        data class User(
            @field:SerializedName("login")
            var login: String,
            @field:SerializedName("avatar_url")
            var avatarUrl: String
        )



}