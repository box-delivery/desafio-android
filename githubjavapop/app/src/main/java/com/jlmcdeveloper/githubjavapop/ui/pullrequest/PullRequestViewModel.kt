package com.jlmcdeveloper.githubjavapop.ui.pullrequest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jlmcdeveloper.githubjavapop.data.RepositoryPullRequest

class PullRequestViewModel(private val repository: RepositoryPullRequest) : ViewModel(){
    val textTitle = MutableLiveData<String?>()
    val pRequestOpen = MutableLiveData<Int?>()
    val pRequestClose = MutableLiveData<Int?>()


}