package com.jlmcdeveloper.githubjavapop.data.api

import retrofit2.Call
import retrofit2.http.*

interface ApiRestGithub {
    @FormUrlEncoded
    @GET("/search/repositories")
    fun listRepository(@Query("language") language: String,
                   @Field("sort") sort: String,
                   @Field("per_page") perPage: Int,
                   @Field("page") page: Int):
            Call<List<RepositoryResponse>>

    @FormUrlEncoded
    @GET("/repos/{user}/{repository}/pulls")
    fun listPullRequest(@Path("user") user: String,
                        @Path("repository") repository: String,
                        @Field("per_page") perPage: Int):
            Call<List<PullRequestResponse>>
}