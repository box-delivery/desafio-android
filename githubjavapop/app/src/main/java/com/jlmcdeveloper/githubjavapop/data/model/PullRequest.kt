package com.jlmcdeveloper.githubjavapop.data.model

import com.jlmcdeveloper.githubjavapop.data.api.PullRequestResponse

data class PullRequest(val title: String,
                       val description: String,
                       val url: String,
                       val user: User) {

    constructor(repository : PullRequestResponse) : this(
        title = repository.title,
        description = repository.body,
        url = repository.html_url,
        user = User(repository.user, repository.updated_at)
    )
}