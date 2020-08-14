package com.jlmcdeveloper.githubjavapop.data.model

import com.jlmcdeveloper.githubjavapop.data.api.RepositoryResponse

data class GitCollection(
    val title: String,
    val description: String,
    val fork: Int,
    val star: Int,
    val user: User){

    constructor(repository : RepositoryResponse) : this(
        title = repository.name,
        description = repository.description,
        fork = repository.forks_count,
        star = repository.stargazers_count,
        user = User(repository.owner, repository.full_name)
    )
}
