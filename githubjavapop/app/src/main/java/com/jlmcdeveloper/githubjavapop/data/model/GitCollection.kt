package com.jlmcdeveloper.githubjavapop.data.model

import com.jlmcdeveloper.githubjavapop.data.api.RepositoryListResponse.RepositoryResponse


data class GitCollection(
    val title: String,
    val description: String,
    val fork: Int,
    val star: Int,
    val user: User){

    constructor(repository : RepositoryResponse) : this(
        title = repository.name,
        description = repository.description,
        fork = repository.forksCount,
        star = repository.stargazersCount,
        user = User(repository.owner, repository.fullName)
    )
}
