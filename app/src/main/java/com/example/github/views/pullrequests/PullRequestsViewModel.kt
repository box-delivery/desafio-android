package com.example.github.views.pullrequests

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.github.data.repo.PullRequestsRepo
import com.example.github.models.PullRequest
import com.example.github.models.Resource

class PullRequestsViewModel
@ViewModelInject
constructor (private val repository: PullRequestsRepo): ViewModel() {

    lateinit var results: LiveData<Resource<List<PullRequest>>>

    fun loadRepoPullRequests(ownerLogin: String, repoName: String) {
        results = if (ownerLogin.isNotEmpty() && repoName.isNotEmpty()) {
            repository.loadPulls(ownerLogin, repoName)
        } else {
            MutableLiveData()
        }
    }
}