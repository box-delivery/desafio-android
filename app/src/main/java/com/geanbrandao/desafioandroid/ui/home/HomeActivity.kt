package com.geanbrandao.desafioandroid.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geanbrandao.desafioandroid.R
import com.geanbrandao.desafioandroid.data.RepositoriesResponse
import com.geanbrandao.desafioandroid.globalExceptionHandle
import com.geanbrandao.desafioandroid.ui.base.BaseActivity
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity() {

    private val viewModel: HomeViewModel by viewModel()

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    private fun createListener() {

    }

    fun getRepositoriesJava(page: Int) {
        val query = "language:Java"
        val sort = "stars"

        disposable = viewModel.getRepositoriesJava(query, sort, page)
            .doOnSubscribe {

            }.doFinally {

            }.subscribeBy(
                onError = {
                    globalExceptionHandle(it)
                },
                onSuccess = {

                }
            )
    }
}