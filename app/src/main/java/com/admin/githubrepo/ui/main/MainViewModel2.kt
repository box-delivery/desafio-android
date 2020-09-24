package com.admin.githubrepo.ui.main

import androidx.lifecycle.*
import com.admin.githubrepo.api.ApiService
import com.admin.githubrepo.api.GithubPull
import com.admin.githubrepo.model.PullSearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MainViewModel2 @Inject constructor(service: ApiService) : ViewModel() {


    private val queryLiveData = MutableLiveData<String>()
    private val repository = GithubPull(service)
    private var xreposti : String = "";

    fun searchRepo(qry: String, reposit: String ) {

        xreposti = reposit
        queryLiveData.postValue(qry)

    }

    val repoResult: LiveData<PullSearchResult> = queryLiveData.switchMap { queryString ->
        liveData {
            val repos =
                repository.getSearchResultStream(queryString,xreposti).asLiveData(Dispatchers.Main)
            emitSource(repos)
        }
    }

    fun lidstScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + MainViewModel2.VISIBLE_THRESHOLD >= totalItemCount) {
            val immutableQuery = queryLiveData.value
            if (immutableQuery != null) {
                viewModelScope.launch {
                    repository.requestMore(immutableQuery)
                }
            }
        }
    }

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }
}