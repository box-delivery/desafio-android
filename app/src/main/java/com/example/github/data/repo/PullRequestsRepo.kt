package com.example.github.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.github.AppExecutors
import com.example.github.data.api.ApiResponse
import com.example.github.data.api.GitService
import com.example.github.data.db.AppDatabase
import com.example.github.models.PullRequest
import com.example.github.models.Resource

class PullRequestsRepo (
    private val appExecutors: AppExecutors,
    private val gitService: GitService,
    private val appDatabase: AppDatabase,
): Repos(){
    private val pullRequestDao = appDatabase.pullRequestDao()

    fun loadPulls(owner: String, repo: String): LiveData<Resource<List<PullRequest>>> {
        return object : NetworkBoundResource<List<PullRequest>, List<PullRequest>>(appExecutors) {
            override fun saveCallResult(item: List<PullRequest>) {
                appDatabase.runInTransaction {
                    item.map {
                        it.repo_name = repo
                    }
                    pullRequestDao.insertMany(item)
                }
            }

            override fun shouldFetch(data: List<PullRequest>?): Boolean {
                return data.isNullOrEmpty()
            }

            override fun loadFromDb(): LiveData<List<PullRequest>> {
                return pullRequestDao.loadByRepositoryName(repo)
            }

            override fun createCall(): LiveData<ApiResponse<List<PullRequest>>> {
                return gitService.searchRepoPullRequests(owner, repo, 1)
            }

        }.asLiveData()
    }
}