package com.geanbrandao.desafioandroid.network

import com.geanbrandao.desafioandroid.data.pull_request.PullRequestResponse
import com.geanbrandao.desafioandroid.data.repositories.Owner
import com.geanbrandao.desafioandroid.data.repositories.RepositoriesResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/search/repositories")
    fun getRepositoriesJava(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Observable<RepositoriesResponse>

    /**
     * Usando esse metodo para pegar o nome e sobrenome do usuario
     * nao é uma coisa eficaz para essa aplicação
     * nao estudei tao a fundo a api assim para pegar o nome na mesma request
     */
    @GET("/users/{username}")
    fun getUserDetails(@Path("username") username: String): Single<Owner>

//   https://api.github.com/search/issues?q=repo%3Arealm%2Frealm-java%20is%3Apr%20is%3Aclosed&per_page=3&page=1

    @GET("/search/issues")
    fun getPullRequest(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Observable<PullRequestResponse>
}