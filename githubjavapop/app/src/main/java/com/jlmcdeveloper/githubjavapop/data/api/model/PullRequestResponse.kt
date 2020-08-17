package com.jlmcdeveloper.githubjavapop.data.api.model

import com.google.gson.annotations.SerializedName

data class PullRequestResponse(
    val title: String,
    val body: String,
    @SerializedName("html_url")
    val url: String,
    val base: Base,
    val user: User
){

    fun getUpdateDate() = base.repo.updatedDate



    data class User(val login: String,
     @SerializedName("avatar_url") val avatarUrl: String)

    data class Base(val repo: Repo)
    data class Repo(@SerializedName("updated_at") val updatedDate: String)

}