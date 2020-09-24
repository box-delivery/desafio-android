package com.geanbrandao.desafioandroid.ui.repos_pull

import android.content.Context
import androidx.lifecycle.ViewModel
import com.geanbrandao.desafioandroid.data.pull_request.PullRequestResponse
import com.geanbrandao.desafioandroid.repositories.ReposRepository
import com.geanbrandao.desafioandroid.repositories.ReposRepositoryImpl
import io.reactivex.Observable

class ReposPullViewModel: ViewModel() {

    private val mRepository: ReposRepository = ReposRepositoryImpl()

    fun getPullRequests(context: Context, fullName: String, page: Int, perPage: Int): Observable<PullRequestResponse> {
        return mRepository.getPullRequests(context, fullName, page, perPage)
    }
}