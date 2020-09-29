package com.example.github.data.api

import androidx.lifecycle.LiveData
import com.example.github.models.PullRequest
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitService {

    @GET("search/repositories")
    fun searchGitRepositories(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 100
    ) : LiveData<ApiResponse<GitRepositoriesResponse>>

    @GET("repos/{login}/{repo}/pulls")
    fun searchRepoPullRequests(
        @Path("login")login: String,
        @Path("repo")repo: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 100
    ): LiveData<ApiResponse<List<PullRequest>>>

}