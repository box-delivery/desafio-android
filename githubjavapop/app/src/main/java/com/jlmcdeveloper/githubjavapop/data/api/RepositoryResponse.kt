package com.jlmcdeveloper.githubjavapop.data.api

data class RepositoryResponse(
    val id: Long,
    val name: String,
    val full_name: String,
    val description: String,
    val forks_count: Int,
    val stargazers_count: Int,
    val owner: Owner){

    data class Owner(val id: Long,
                     val login: String,
                     val avatar_url: String,
                     val html_url: String)
}