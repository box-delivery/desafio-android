
package com.admin.githubrepo.api
import com.admin.githubrepo.model.Pull
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val IN_QUALIFIER = "in:name,description"

/**
 * Github API communication setup via Retrofit.
 */
interface ApiService {
    /**
     * Get repos ordered by stars.
     */
    //repos/<criador>/<repositório>/pulls
    @GET("search/repositories?sort=stars")
    suspend fun getRepo(
            @Query("q") query: String,
            @Query("page") page: Int,
            @Query("per_page") itemsPerPage: Int
    ): RepoSearchResponse

    @GET("repos/{criador}/{reposit}/pulls")
    suspend fun getPull(
        @Path("criador") criador: String,
        @Path("reposit") reposit: String
    ): List<Pull>

}