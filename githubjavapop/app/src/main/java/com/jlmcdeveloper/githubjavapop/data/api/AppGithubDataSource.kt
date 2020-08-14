package com.jlmcdeveloper.githubjavapop.data.api

import com.jlmcdeveloper.githubjavapop.data.model.GitCollection
import com.jlmcdeveloper.githubjavapop.data.model.PullRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppGithubDataSource(private val apiRest: ApiRestGithub) : GithubDataSource {

    override fun listRepository(page: Int, success: (List<GitCollection>) -> Unit, failure: (String) -> Unit) {
        val call =apiRest.listRepository(
            ApiEndPoint.language,
            ApiEndPoint.sort,
            ApiEndPoint.per_page, page)

        call.enqueue(object : Callback<List<RepositoryResponse>> {
            override fun onResponse( call: Call<List<RepositoryResponse>>,
                response: Response<List<RepositoryResponse>>) {

                if (response.isSuccessful) {
                    val collection = mutableListOf<GitCollection>()
                    response.body()?.forEach { collection.add(GitCollection(it)) }

                    if(response.body() != null)
                        success(collection)
                    else
                        failure("response.body() = null")
                }
            }

            override fun onFailure(call: Call<List<RepositoryResponse>>, t: Throwable) {
                failure(t.message?: "error: onFailure")
            }
        })
    }



    override fun listPullRequest(page: Int, userName: String, repositoryName: String,
                                 success: (List<PullRequest>) -> Unit, failure: (String) -> Unit) {

        val call =apiRest.listPullRequest(userName, repositoryName, page)
        call.enqueue(object: Callback<List<PullRequestResponse>> {

            override fun onResponse(
                call: Call<List<PullRequestResponse>>,
                response: Response<List<PullRequestResponse>>) {

                if (response.isSuccessful) {
                    val pullRequest = mutableListOf<PullRequest>()
                    response.body()?.forEach { pullRequest.add(PullRequest(it)) }

                    if(response.body() != null)
                        success(pullRequest)
                    else
                        failure("response.body() = null")
                }
            }

            override fun onFailure(call: Call<List<PullRequestResponse>>, t: Throwable) {
                failure(t.message?: "error: onFailure")
            }
        })
    }
}