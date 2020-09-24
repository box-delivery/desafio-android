package com.geanbrandao.desafioandroid.repositories

import android.content.Context
import com.geanbrandao.desafioandroid.data.Item
import com.geanbrandao.desafioandroid.data.RepositoriesResponse
import com.geanbrandao.desafioandroid.network.RetrofitInitializer
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ReposRepositoryImpl : ReposRepository {

    override fun getRepositoriesJava(context: Context, query: String, sort: String, page: Int, perPage: Int): Observable<Item> {
        Timber.tag("DEBUG").d("Chegou aqui ")
        return Observable.create<Item> { emitter ->
            RetrofitInitializer(context).createService()
                    .getRepositoriesJava(query, sort, page, perPage)
                    .map { repoResponse ->
                        Timber.tag("DEBUG").d("MAP")
                        repoResponse.items.forEach { item ->
                            Timber.tag("DEBUG").d("item - ${item.url}")
                            RetrofitInitializer(context).createService().getUserDetails(item.owner.login)
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

    override fun getRepositoriesJava2(context: Context, query: String, sort: String, page: Int, perPage: Int): Observable<RepositoriesResponse> {
        return RetrofitInitializer(context).createService().getRepositoriesJava(query, sort, page, perPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}