package com.jlmcdeveloper.githubjavapop.data

import com.jlmcdeveloper.githubjavapop.data.api.ApiEndPoint
import com.jlmcdeveloper.githubjavapop.data.api.GithubDataSource

class Repository(private val githubDataSource: GithubDataSource) {
    fun test() {
        githubDataSource.listRepository(1,ApiEndPoint.language,success = {
            val repository = it[0]
            println("aqui, ok user: ${repository.user}")

        }, failure = {
            println("aqui, tipo do erro: $it")
        })
    }

}