package com.jlmcdeveloper.githubjavapop.data.api

data class PullRequestResponse(
    val title: String,
    val body: String,
    val html_url: String,
    val updated_at: String,
    val user: User){

    data class User(val login: String,
                    val avatar_url: String)
}