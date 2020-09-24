package com.geanbrandao.desafioandroid.network

import com.geanbrandao.desafioandroid.data.Owner
import com.geanbrandao.desafioandroid.data.RepositoriesResponse
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
}