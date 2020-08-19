package com.jlmcdeveloper.githubjavapop.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jlmcdeveloper.githubjavapop.data.RepositoryMain
import com.jlmcdeveloper.githubjavapop.data.model.GitCollection

class MainViewModel(private val repository: RepositoryMain) : ViewModel(){
    val listData = MutableLiveData<MutableList<GitCollection>>(mutableListOf())
    val loadingList = MutableLiveData(false)
    val loadingNewItems = MutableLiveData(false)
    val message = MutableLiveData<String>()



    //--- referencia para buscar novas paginas -----
    fun positionList(pos: Int){
        if(!loadingList.value!! && !loadingNewItems.value!! && listData.value!!.isNotEmpty()) {
            if (listData.value!!.size <= pos+1)
                loadListGitCollection()
        }
    }

    // -------- info para o pullRequest------------
    fun setRepository(gitCollection: GitCollection) {
        repository.setCollection(gitCollection)
    }


    // -- carregar lista se ainda não foi carregado --
    fun updateList() {
        loadListGitCollection()
    }

    //--------- carregamento incial ---------
    fun load(){
        if(repository.gitCollection.size > 0)
            listData.postValue(repository.gitCollection)
        else
            loadListGitCollection()
    }



    private fun error(info: String){
        message.postValue(info)
        loading(false)
    }


    private fun loadListGitCollection() {
        loading(true)

        repository.moreCollection({
            listData.postValue(it)
            loading(false)
        },{ error("erro ao carregar a lista de Repositorios") })
    }


    private fun loading(value: Boolean){
        if(value){
            if (listData.value!!.isEmpty() || !value)
                loadingList.postValue(true)
            else
                loadingNewItems.postValue(true)
        }else {
            loadingList.postValue(false)
            loadingNewItems.postValue(false)
        }
    }
}