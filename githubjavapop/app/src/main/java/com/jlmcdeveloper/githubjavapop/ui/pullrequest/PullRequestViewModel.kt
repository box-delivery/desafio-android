package com.jlmcdeveloper.githubjavapop.ui.pullrequest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jlmcdeveloper.githubjavapop.data.Repository

class PullRequestViewModel(val repository: Repository) : ViewModel(){
    val textTitle = MutableLiveData<String?>()
    val pRequestOpen = MutableLiveData<Int?>()
    val pRequestClose = MutableLiveData<Int?>()


}