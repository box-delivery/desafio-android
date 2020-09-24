package com.geanbrandao.desafioandroid.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.geanbrandao.desafioandroid.R
import com.geanbrandao.desafioandroid.data.repositories.RepositoriesResponse
import com.geanbrandao.desafioandroid.globalExceptionHandle
import com.geanbrandao.desafioandroid.goToActivity
import com.geanbrandao.desafioandroid.ui.adapter.ReposAdapter
import com.geanbrandao.desafioandroid.ui.base.BaseActivity
import com.geanbrandao.desafioandroid.ui.repos_pull.ReposPullActivity
import com.geanbrandao.desafioandroid.utils.PaginationScrollListener
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.component_toolbar.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class HomeActivity : BaseActivity() {

    companion object {
        const val PER_PAGE = 10

        const val PAGE_KEY = "PAGE_KEY"
        const val DATA_KEY = "DATA_KEY"
    }

    private val viewModel: HomeViewModel by viewModel()

    private var disposable: Disposable? = null

    private var isLastPage: Boolean = false

    private var isLoading: Boolean = false

    private var page: Int = 0
    private var response: RepositoriesResponse? = null

    private lateinit var layoutManager: LinearLayoutManager

    private val adapter: ReposAdapter by lazy {
        ReposAdapter(
                this,
                {
                    repoClick(it.full_name)
                }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        savedInstanceState?.let {
            with(it) {
                Timber.d("DEBUG - restaurando do estado anterior")
                page = getInt(PAGE_KEY, 0)
                response = getSerializable(DATA_KEY) as RepositoriesResponse?

                response?.let {
                    adapter.clear()
                    it.items.forEach { item ->
                        adapter.add(item)
                    }

                } ?: run {
                    Timber.d("DEBUG - response eh NULL entao faz a request")
                    getRepositoriesJava()
                }
            }

        } ?: run {
            Timber.d("DEBUG - sem nada para restaurar do estado anterior")
            getRepositoriesJava()
        }

        createListener()
    }

    private fun createListener() {
        setupFields()
        recycler_repos.adapter = adapter
        layoutManager = LinearLayoutManager(this)
        recycler_repos.layoutManager = layoutManager

        paginationManager()

        refresh.setOnRefreshListener {
            // clear adapter
            refresh.isRefreshing = true
            page = 0
            getRepositoriesJava()
        }
    }

    private fun paginationManager() {
        recycler_repos.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                getRepositoriesJava()
            }

        })
    }

    private fun repoClick(fullName: String) {
        val intent = Intent(this, ReposPullActivity::class.java)
        intent.putExtra(ReposPullActivity.FULLNAME_KEY, fullName)
        startActivity(intent)
    }

    private fun setupFields() {
        toolbar.text_title.text = "Repositorios"
        toolbar.image_back.visibility = View.GONE
    }

    private fun getRepositoriesJava() {
        val query = "language:Java"
        val sort = "stars"
        Timber.tag("DEBUG").d("Chamou")

        // quando faz a subscricao chama o onrefresh

        disposable = viewModel.getRepositoriesJava2(this, query, sort, ++page, PER_PAGE)
                .doOnSubscribe {
                    if (isLoading) {
                        // mostra o loading no bottom da pagina
                        progress_bottom.visibility = View.VISIBLE
                    } else {
                        refresh.isRefreshing = true
                    }
                }
                .subscribeBy(
                        onNext = {
                            Timber.tag("DEBUG").d("Page  ${page}")
                            if(page == 1) {
                                adapter.clear()
                                response = it
                            } else {
                                response?.items?.addAll(it.items)
                            }

                            isLastPage = (page * PER_PAGE) >= it.total_count

                            it.items.forEach { item ->
                                adapter.add(item)
                            }
                            refresh.isRefreshing = false
                            progress_bottom.visibility = View.GONE
                            isLoading = false
                        },
                        onError = {
                            refresh.isRefreshing = false
                            progress_bottom.visibility = View.GONE
                            isLoading = false
                            globalExceptionHandle(it)
                        },
                        onComplete = {}
                )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Timber.d("DEBUG - salvando os paranaue")
        outState.putInt(PAGE_KEY, page)
        outState.putSerializable(DATA_KEY, response)
        super.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        disposable?.dispose()
    }

    private fun stepBefore() {

    }

    override fun onBackPressed() {
        super.onBackPressed()
        stepBefore()
    }
}