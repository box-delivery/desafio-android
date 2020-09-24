package com.geanbrandao.desafioandroid.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.geanbrandao.desafioandroid.data.repositories.Item
import com.geanbrandao.desafioandroid.data.repositories.RepositoriesResponse
import com.geanbrandao.desafioandroid.repositories.ReposRepository
import com.geanbrandao.desafioandroid.repositories.ReposRepositoryImpl
import io.reactivex.Observable

class HomeViewModel: ViewModel() {
    private val mRepository: ReposRepository = ReposRepositoryImpl()

    fun getRepositoriesJava(context: Context, query: String, sort: String, page: Int, perPage: Int): Observable<Item> {
        return mRepository.getRepositoriesJava(context, query, sort, page, perPage)
    }

    fun getRepositoriesJava2(context: Context, query: String, sort: String, page: Int, perPage: Int): Observable<RepositoriesResponse> {
        return mRepository.getRepositoriesJava2(context, query, sort, page, perPage)
    }
}