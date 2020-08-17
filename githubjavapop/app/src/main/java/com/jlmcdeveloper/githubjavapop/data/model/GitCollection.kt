package com.jlmcdeveloper.githubjavapop.data.model

import com.jlmcdeveloper.githubjavapop.data.api.model.RepositoryListResponse.RepositoryResponse


data class GitCollection(
    var title: String,
    var description: String,
    var fork: Int,
    var star: Int,
    var user: User){

    constructor() : this("","",0,0, User())

    constructor(repository : RepositoryResponse) : this(
        title = repository.name,
        description = repository.description,
        fork = repository.forksCount,
        star = repository.stargazersCount,
        user = User(repository.owner, repository.fullName)
    )
}
