package com.jlmcdeveloper.githubjavapop.ui.pullrequest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jlmcdeveloper.githubjavapop.data.RepositoryPullRequest
import com.jlmcdeveloper.githubjavapop.data.model.PullRequest

class PullRequestViewModel(private val repository: RepositoryPullRequest) : ViewModel(){
    val textTitle = MutableLiveData<String?>()
    val pRequestOpen = MutableLiveData<Int?>()
    val pRequestClose = MutableLiveData<Int?>()
    val listData = MutableLiveData<MutableList<PullRequest>>(mutableListOf())
    val loadingList = MutableLiveData(false)
    val message = MutableLiveData<String>()




    // -- carregar lista se ainda não foi carregado --
    fun updateList() {
        loadListPullRequest()
    }

    //--------- carregamento incial ---------
    fun load(){
        if(repository.pullRequests.size > 0) {
            listData.postValue(repository.pullRequests)
            start()
        }else
            loadListPullRequest()
    }



    private fun error(info: String){
        message.postValue(info)
        loadingList.postValue(false)
    }


    private fun loadListPullRequest() {
        loadingList.postValue(true)

        repository.listPullRequest({
            listData.postValue(it)
            loadingList.postValue(false)
            start()
        },{ error("erro ao carregar a lista de PullRequest") })
    }


    private fun start(){
        textTitle.postValue(repository.collection.title)
        pRequestOpen.postValue(repository.getQtPROpen())
        pRequestClose.postValue(repository.getQtPRClose())
    }
}