package com.admin.githubrepo.api

import android.util.Log
import com.admin.githubrepo.model.Pull
import com.admin.githubrepo.model.PullSearchResult
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

// GitHub page API is 1 based: https://developer.github.com/v3/#pagination
private const val GITHUB_STARTING_PAGE_INDEX = 1

/**
 * Repository class that works with local and remote data sources.
 */
@ExperimentalCoroutinesApi
 class GithubPull(private val service: ApiService) {


    private val inMemoryCache = mutableListOf<Pull>()

    private val searchResults = ConflatedBroadcastChannel<PullSearchResult>()

    private var lastRequestedPage = GITHUB_STARTING_PAGE_INDEX

    private var isRequestInProgress = false

    private var xreposit: String? = null

    suspend fun getSearchResultStream(query: String, reposit: String): Flow<PullSearchResult> {
        Log.d("GithubRepository", "New query: $query")
        lastRequestedPage = 1
        xreposit = reposit
        inMemoryCache.clear()
        requestAndSaveData(query)

        return searchResults.asFlow()
    }

    suspend fun requestMore(query: String) {
        if (isRequestInProgress) return
        val successful = requestAndSaveData(query)
        if (successful) {
            lastRequestedPage++
        }
    }

    suspend fun retry(query: String, reposit: String) {
        if (isRequestInProgress) return
        requestAndSaveData(query)
    }

    suspend fun requestAndSaveData(query: String): Boolean {
        isRequestInProgress = true
        var successful = false

        try {
            val response = xreposit?.let { service.getPull(query, it) }
            Log.d("GithubRepository", "response $response")
            val repos = response
            inMemoryCache.addAll(repos as Collection<Pull>)
            //val reposByName = reposByName(query)
            searchResults.offer(PullSearchResult.Success(repos))
            successful = true
        } catch (exception: IOException) {
            searchResults.offer(PullSearchResult.Error(exception))
        } catch (exception: HttpException) {
            searchResults.offer(PullSearchResult.Error(exception))
        }
        isRequestInProgress = false
        return successful
    }


}
