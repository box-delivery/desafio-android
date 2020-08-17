package com.jlmcdeveloper.githubjavapop.data

import com.jlmcdeveloper.githubjavapop.data.api.ApiEndPoint
import com.jlmcdeveloper.githubjavapop.data.api.GithubDataSource
import com.jlmcdeveloper.githubjavapop.data.model.GitCollection

class RepositoryMain(private val githubDataSource: GithubDataSource, private val collection: GitCollection) {

    val gitCollection = mutableListOf<GitCollection>()

    fun moreCollection(success : (List<GitCollection>) -> Unit, failure: (String) -> Unit) {
        githubDataSource.listRepository(getPage(), ApiEndPoint.language,success = {
            gitCollection.addAll(it)
            success(gitCollection)

        }, failure = { failure(it) })
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