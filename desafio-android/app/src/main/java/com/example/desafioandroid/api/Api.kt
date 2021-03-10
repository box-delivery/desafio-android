package com.example.desafioandroid.api

import com.example.desafioandroid.models.Pull
import com.example.desafioandroid.models.responses.RepositoriosResponse
import com.example.desafioandroid.models.User
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @GET("search/repositories?q=language:Java&sort=stars&por_page=15")
    fun getRepositories(
        @QueryMap parameters : HashMap<String, String>
    ) : Call<RepositoriosResponse>

    @GET("users/{login}")
    fun getUser(
        @Path("login") login: String
    ): Call<User>

    @GET("repos/{login}/{repository}/pulls")
    fun getPulls(
        @Path("login") login: String,
        @Path("repository") repository: String
    ): Call<ArrayList<Pull>>
}