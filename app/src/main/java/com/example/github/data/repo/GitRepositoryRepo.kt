package com.example.github.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.example.github.AppExecutors
import com.example.github.data.api.ApiResponse
import com.example.github.data.api.ApiSuccessResponse
import com.example.github.data.api.GitRepositoriesResponse
import com.example.github.data.api.GitService
import com.example.github.data.db.AppDatabase
import com.example.github.data.db.GitRepositoryDao
import com.example.github.models.GitRepositoriesSearchResult
import com.example.github.models.GitRepository
import com.example.github.models.Resource
import com.example.github.util.AbsentLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.util.concurrent.TimeUnit


class GitRepositoryRepo constructor(
    private val appExecutors: AppExecutors,
    private val gitService: GitService,
    private val appDatabase: AppDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : Repos() {

    private val gitRepositoryDao: GitRepositoryDao = appDatabase.gitRepositoryDao()

    private val repoListRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)


    fun searchRepos(query: String): LiveData<Resource<List<GitRepository>>> {
        return object : NetworkBoundResource<List<GitRepository>, GitRepositoriesResponse>(appExecutors) {
            override fun saveCallResult(item: GitRepositoriesResponse) {
                val searchResult = GitRepositoriesSearchResult(
                    query = query,
                    gitRepositoryIds = item.items.map { it.id },
                    totalCount = item.total,
                    next = item.nextPage
                )

                appDatabase.runInTransaction {
                    gitRepositoryDao.insertManyGitRepos(item.items)
                    gitRepositoryDao.insertSearchResult(searchResult)
                }
            }

            override fun shouldFetch(data: List<GitRepository>?) = data == null

            override fun loadFromDb(): LiveData<List<GitRepository>> = gitRepositoryDao.searchLiveData(query).switchMap { searchResult ->
                if (searchResult == null) {
                    AbsentLiveData.create()
                } else {
                    gitRepositoryDao.loadGitReposById(searchResult.gitRepositoryIds)
                }
            }


            override fun createCall(): LiveData<ApiResponse<GitRepositoriesResponse>> =
                gitService.searchGitRepositories(query, "stars", 1)

            override fun processResponse(
                response: ApiSuccessResponse<GitRepositoriesResponse>
            ): GitRepositoriesResponse =
                response.body.also {
                    it.nextPage = response.nextPage
                }

            override fun onFetchFailed() {
                repoListRateLimit.reset(query)
            }


        }.asLiveData()
    }

    fun searchNextPage(query: String): LiveData<Resource<Boolean>> {
        val fetchNextSearchPageTask = FetchNextSearchPageTask(
            query = query,
            githubService = gitService,
            db = appDatabase
        )
        appExecutors.networkIO().execute(fetchNextSearchPageTask)
        return fetchNextSearchPageTask.liveData
    }
}