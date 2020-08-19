package com.jlmcdeveloper.githubjavapop.data.api

import com.jlmcdeveloper.githubjavapop.data.api.model.PullRequestResponse
import com.jlmcdeveloper.githubjavapop.data.api.model.RepositoryListResponse
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Query

interface ApiRestGithub {

    @GET("/search/repositories")
    fun listRepository(@Query("q") language : String,
                       @Query("sort") sort: String,
                       @Query("per_page") perPage: Int,
                       @Query("page") page: Int):
            Call<RepositoryListResponse>


    @GET("/repos/{user}/{repository}/pulls")
    fun listPullRequest(@Path("user") user: String,
                        @Path("repository") repository: String):
            Call<List<PullRequestResponse>>
}