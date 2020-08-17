package com.jlmcdeveloper.githubjavapop.data.api

import com.jlmcdeveloper.githubjavapop.data.model.GitCollection
import com.jlmcdeveloper.githubjavapop.data.model.PullRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppGithubDataSource(private val apiRest: ApiRestGithub) : GithubDataSource {

    override fun listRepository(page: Int, language: String, success: (List<GitCollection>) -> Unit, failure: (String) -> Unit) {
        val call =apiRest.listRepository(
            ApiEndPoint.queryString("language" to language),
            ApiEndPoint.sort,
            ApiEndPoint.perPage, page)

        call.enqueue(object : Callback<RepositoryListResponse> {
            override fun onResponse( call: Call<RepositoryListResponse>,
                response: Response<RepositoryListResponse>) {

                if (response.isSuccessful) {
                    val collection = mutableListOf<GitCollection>()
                    response.body()?.repository?.forEach { collection.add(GitCollection(it)) }

                    if(response.body() != null)
                        success(collection)
                    else
                        failure("response.body() = null")
                }
                else
                    failure("errorBody: ${response.errorBody()}")
            }

            override fun onFailure(call: Call<RepositoryListResponse>, t: Throwable) {
                failure("error: onFailure: ${t.message}")
            }
        })
    }



    override fun listPullRequest(page: Int, userName: String, repositoryName: String,
                                 success: (List<PullRequest>) -> Unit, failure: (String) -> Unit) {

        val call= apiRest.listPullRequest(userName, repositoryName,
            ApiEndPoint.perPage, page)

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
                else
                    failure("errorBody: ${response.errorBody()}")
            }

            override fun onFailure(call: Call<List<PullRequestResponse>>, t: Throwable) {
                failure(t.message?: "error: onFailure")
            }
        })
    }
}