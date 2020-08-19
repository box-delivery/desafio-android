package com.jlmcdeveloper.githubjavapop.data

import com.jlmcdeveloper.githubjavapop.data.api.ApiEndPoint
import com.jlmcdeveloper.githubjavapop.data.api.GithubDataSource
import com.jlmcdeveloper.githubjavapop.data.model.GitCollection
import com.jlmcdeveloper.githubjavapop.data.model.PullRequest


class RepositoryPullRequest(private val githubDataSource: GithubDataSource, val collection: GitCollection) {

    val pullRequests = mutableListOf<PullRequest>()
    var running = false

    fun listPullRequest(success : (MutableList<PullRequest>) -> Unit, failure: (String) -> Unit) {
        if(!running) {
            running = true
            githubDataSource.listPullRequest(collection.user.name, collection.title, success = {
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

    fun getQtPROpen(): Int{
        var qt = 0
        pullRequests.forEach { if(it.state == "open") qt++}
        return qt
    }

    fun getQtPRClose(): Int{
        var qt = 0
        pullRequests.forEach { if(it.state == "closed") qt++}
        return qt
    }
}