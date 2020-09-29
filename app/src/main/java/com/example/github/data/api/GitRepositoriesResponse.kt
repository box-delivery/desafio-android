package com.example.github.data.api

import com.example.github.models.GitRepository
import com.google.gson.annotations.SerializedName

data class GitRepositoriesResponse (
    @SerializedName("total_count")
    val total: Int = 0,
    @SerializedName("items")
    val items: List<GitRepository>
) {
    var nextPage: Int? = null
}
