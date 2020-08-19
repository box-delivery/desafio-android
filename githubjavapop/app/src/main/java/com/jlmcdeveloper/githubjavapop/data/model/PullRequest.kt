package com.jlmcdeveloper.githubjavapop.data.model

import com.jlmcdeveloper.githubjavapop.data.api.model.PullRequestResponse

data class PullRequest(
    val title: String,
    val body: String,
    val url: String,
    val state: String,
    val user: User
) {

    constructor(repository: PullRequestResponse) : this(
        title = repository.title,
        body = repository.body,
        url = repository.url,
        state = repository.state,
        user = User(repository.user, repository.getUpdateDate())
    )
}