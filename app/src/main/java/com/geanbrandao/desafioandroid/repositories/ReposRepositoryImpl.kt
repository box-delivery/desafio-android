package com.geanbrandao.desafioandroid.repositories

import android.content.Context
import com.geanbrandao.desafioandroid.data.pull_request.PullRequestResponse
import com.geanbrandao.desafioandroid.data.repositories.Item
import com.geanbrandao.desafioandroid.data.repositories.RepositoriesResponse
import com.geanbrandao.desafioandroid.network.RetrofitInitializer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ReposRepositoryImpl : ReposRepository {

    override fun getRepositoriesJava(
        context: Context,
        query: String,
        sort: String,
        page: Int,
        perPage: Int
    ): Observable<Item> {
        Timber.tag("DEBUG").d("Chegou aqui ")
        return Observable.create<Item> { emitter ->
            RetrofitInitializer(context).createService()
                .getRepositoriesJava(query, sort, page, perPage)
                .map { repoResponse ->
                    Timber.tag("DEBUG").d("MAP")
                    repoResponse.items.forEach { item ->
                        Timber.tag("DEBUG").d("item - ${item.url}")
                        RetrofitInitializer(context).createService()
                            .getUserDetails(item.owner.login)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeBy(
                                onError = {
                                    emitter.onError(it)
                                },
                                onSuccess = {
                                    item.owner.name = it.name
                                    emitter.onNext(item)
                                }
                            )
                    }
                }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    override fun getRepositoriesJava2(
        context: Context,
        query: String,
        sort: String,
        page: Int,
        perPage: Int
    ): Observable<RepositoriesResponse> {
        return RetrofitInitializer(context).createService()
            .getRepositoriesJava(query, sort, page, perPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getPullRequests(context: Context, page: Int, perPage: Int): Observable<PullRequestResponse> {
        val qOpen = "" // primeira eh open
        val qTotal = ""
        return Observable.zip(
            RetrofitInitializer(context).createService().getPullRequest(qOpen, page, perPage),
            RetrofitInitializer(context).createService().getPullRequest(qTotal, page, perPage),
            BiFunction<PullRequestResponse, PullRequestResponse, PullRequestResponse> { p1: PullRequestResponse, p2: PullRequestResponse ->
                val closed = p1.total_count - p2.total_count // abertos menos o total
                val p3 = p1
                p3.closed = closed
                p3.open = p1.total_count
                return@BiFunction p3
            }
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}