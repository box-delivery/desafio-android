package com.jlmcdeveloper.githubjavapop.data

import com.jlmcdeveloper.githubjavapop.data.api.ApiEndPoint
import com.jlmcdeveloper.githubjavapop.data.api.GithubDataSource
import com.jlmcdeveloper.githubjavapop.data.model.GitCollection
import com.jlmcdeveloper.githubjavapop.data.model.PullRequest


class RepositoryPullRequest(private val githubDataSource: GithubDataSource, private val collection: GitCollection) {

    val pullRequests = mutableListOf<PullRequest>()
    var running = false

    fun moreCollection(success : (MutableList<PullRequest>) -> Unit, failure: (String) -> Unit) {
        if(!running) {
            running = true
            githubDataSource.listPullRequest(
                getPage(),
                collection.user.name,
                collection.title,

                success = {
                    pullRequests.addAll(it)
                    success(pullRequests)
                    running= false
                },
                failure = {
                    failure(it)
                    running= false
                })
        }
    }


    private fun getPage(): Int{
        return (pullRequests.size / ApiEndPoint.perPage) + 1
    }
}