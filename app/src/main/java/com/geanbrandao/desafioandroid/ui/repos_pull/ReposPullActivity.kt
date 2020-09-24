package com.geanbrandao.desafioandroid.ui.repos_pull

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.geanbrandao.desafioandroid.*
import com.geanbrandao.desafioandroid.data.pull_request.PullRequestResponse
import com.geanbrandao.desafioandroid.data.repositories.RepositoriesResponse
import com.geanbrandao.desafioandroid.ui.adapter.PullRequestAdapter
import com.geanbrandao.desafioandroid.ui.base.BaseActivity
import com.geanbrandao.desafioandroid.ui.home.HomeActivity
import com.geanbrandao.desafioandroid.utils.PaginationScrollListener
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_repos_pull.*
import kotlinx.android.synthetic.main.activity_repos_pull.progress_bottom
import kotlinx.android.synthetic.main.activity_repos_pull.refresh
import kotlinx.android.synthetic.main.activity_repos_pull.toolbar
import kotlinx.android.synthetic.main.component_toolbar.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class ReposPullActivity : BaseActivity() {

    companion object {
        const val PER_PAGE = 10

        const val PAGE_KEY = "PAGE_KEY"
        const val DATA_KEY = "DATA_KEY"
        const val OPENED_KEY = "OPENED_KEY"
        const val CLOSED_KEY = "CLOSED_KEY"

        const val FULLNAME_KEY = "FULLNAME_KEY"
    }

    private val viewModel: ReposPullViewModel by viewModel()

    private var disposable: Disposable? = null

    private var isLastPage: Boolean = false

    private var isLoading: Boolean = false

    private var page: Int = 0

    private var opened: Int = 0

    private var closed: Int = 0


    private var response: PullRequestResponse? = null

    private lateinit var layoutManager: LinearLayoutManager

    private val adapter: PullRequestAdapter by lazy {
        PullRequestAdapter(this,
                {
                    goToPull(it.html_url)
                })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repos_pull)

        savedInstanceState?.let {
            with(it) {
                Timber.d("DEBUG - restaurando do estado anterior")
                page = getInt(HomeActivity.PAGE_KEY, 0)

                response = getSerializable(HomeActivity.DATA_KEY) as PullRequestResponse?

                closed = getInt(CLOSED_KEY, 0)

                opened = getInt(OPENED_KEY, 0)


                text_opened.text = getString(R.string.repos_pull_text_opened, opened)
                text_closed.text = getString(R.string.repos_pull_text_closed, closed)

                response?.let {
                    adapter.clear()
                    it.items.forEach { item ->
                        adapter.add(item)
                    }

                } ?: run {
                    Timber.d("DEBUG - response eh NULL entao faz a request")
                    getPullRequests()
                }
            }
        } ?: run {
            Timber.d("DEBUG - sem nada para restaurar do estado anterior")
            getPullRequests()
        }

        createListener()
    }

    private fun createListener() {
        recycler_pull.adapter = adapter
        layoutManager = LinearLayoutManager(this)
        recycler_pull.layoutManager = layoutManager

        setupFields()
        paginationManager()

        refresh.setOnRefreshListener {
            refresh.isRefreshing = true
            page = 0
            getPullRequests()
        }

    }

    private fun setupFields() {
        toolbar.text_title.text = intent.getStringExtra(FULLNAME_KEY)?.split("/")?.get(1) ?: ""
        toolbar.image_back.increaseHitArea(20f)
        toolbar.image_back.setOnClickListener {
            stepBefore()
        }
    }

    private fun paginationManager() {
        recycler_pull.addOnScrollListener(object : PaginationScrollListener(layoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                getPullRequests()
            }

        })
    }

    private fun goToPull(url: String) {
        openNewTabWindow(url)
    }

    private fun getPullRequests() {
        val fullname = intent.getStringExtra(FULLNAME_KEY)

        fullname?.let {

        } ?: run {
            showDialogError("Erro ao obter pull requests")
                    .setOnDismissListener {
                        stepBefore()
                    }
        }

        disposable = viewModel.getPullRequests(this, fullname, ++page, PER_PAGE)
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
                            text_opened.text = getString(R.string.repos_pull_text_opened, it.open)
                            text_closed.text = getString(R.string.repos_pull_text_closed, it.closed)

                            opened = it.open
                            closed = it.closed

                            if (page == 1) {
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
        outState.putInt(OPENED_KEY, opened)
        outState.putInt(CLOSED_KEY, closed)
        super.onSaveInstanceState(outState)
    }

    private fun stepBefore() {
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        stepBefore()
    }

    override fun onStop() {
        super.onStop()
        disposable?.dispose()
    }
}