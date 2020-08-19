package com.jlmcdeveloper.githubjavapop.data

import com.jlmcdeveloper.githubjavapop.data.api.ApiEndPoint
import com.jlmcdeveloper.githubjavapop.data.api.GithubDataSource
import com.jlmcdeveloper.githubjavapop.data.model.GitCollection

class RepositoryMain(private val githubDataSource: GithubDataSource, private val collection: GitCollection) {

    val gitCollection = mutableListOf<GitCollection>()
    var running = false

    fun moreCollection(success : (MutableList<GitCollection>) -> Unit, failure: (String) -> Unit) {
        if(!running) {
            running = true

            githubDataSource.listRepository(getPage(), ApiEndPoint.language, success = {
                gitCollection.addAll(it)
                success(gitCollection)
                running= false

            }, failure = {
                failure(it)
                running = false
            })
        }
    }

    fun setCollection(gitCollection: GitCollection){
        collection.apply {
            title = gitCollection.title
            user = gitCollection.user
        }
    }

    private fun getPage(): Int{
        return (gitCollection.size / ApiEndPoint.perPage) + 1
    }
}