package com.jlmcdeveloper.githubjavapop.data.api

import com.jlmcdeveloper.githubjavapop.data.model.GitCollection
import com.jlmcdeveloper.githubjavapop.data.model.PullRequest

interface GithubDataSource {
    fun listRepository(page: Int, language: String, success : (List<GitCollection>) -> Unit, failure: (String) -> Unit)
    fun listPullRequest( userName: String, repositoryName: String,
                        success : (List<PullRequest>) -> Unit, failure: (String) -> Unit)
}