package com.geanbrandao.desafioandroid.repositories

import android.content.Context
import com.geanbrandao.desafioandroid.data.Item
import com.geanbrandao.desafioandroid.data.RepositoriesResponse
import io.reactivex.Observable

interface ReposRepository {
    fun getRepositoriesJava(context: Context, query: String, sort: String, page: Int, perPage: Int): Observable<Item>
    fun getRepositoriesJava2(context: Context, query: String, sort: String, page: Int, perPage: Int): Observable<RepositoriesResponse>
}