package com.jlmcdeveloper.githubjavapop.data.api

import com.google.gson.annotations.SerializedName

data class RepositoryListResponse(
    @SerializedName("items") val repository: List<RepositoryResponse>) {


    data class RepositoryResponse(
        val id: Long,
        val name: String,
        @SerializedName("full_name") val fullName: String,
        val description: String,
        @SerializedName("forks_count") val forksCount: Int,
        @SerializedName("stargazers_count") val stargazersCount: Int,
        val owner: Owner
    )

    data class Owner(
        val id: Long,
        val login: String,
        @SerializedName("avatar_url") val avatarUrl: String,
        @SerializedName("html_url") val htmlUrl: String
    )
}