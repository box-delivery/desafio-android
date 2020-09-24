package com.admin.githubrepo.ui.main

import android.annotation.SuppressLint
import android.os.Build

import android.os.Bundle
import android.widget.Toast
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.admin.githubrepo.R
import com.admin.githubrepo.adapter.CellClickListene
import com.admin.githubrepo.adapter.PullAdapter
import com.admin.githubrepo.base.BaseActivity
import com.admin.githubrepo.model.Pull
import com.admin.githubrepo.model.PullSearchResult
import com.admin.githubrepo.model.Repo
import kotlinx.android.synthetic.main.pull_activity.*
import kotlinx.android.synthetic.main.pull_view_item.*

class PullActivity : BaseActivity<MainViewModel2>(), CellClickListene {

    lateinit var bundle: Bundle
    private val adapter = PullAdapter(this)
    lateinit var listpull: RecyclerView
    private var layoutManager = LinearLayoutManager(this)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)

        bundle = intent.extras!!
        listpull = findViewById(R.id.listpull)

        // add dividers between RecyclerView's row items
        val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        listpull.addItemDecoration(decoration)

        listpull.layoutManager = layoutManager

        setupScrollListener()
        initAdapter()
        updateRepoListFromInput()
        configtoolba()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun configtoolba(){

        toolbar.title = bundle.getString("reposit")
        toolbar.setNavigationIcon(R.drawable.backwhite)
        toolbar.setNavigationOnClickListener {

            onBackPressed()
        }
    }

    override fun onCellClickListener(repo: Repo) {

    }

    override fun onCellClickListener2(data: Pull) {

    }


    private fun updateRepoListFromInput() {

        listpull.scrollToPosition(0)
        viewModel.searchRepo(bundle.getString("name").toString(),
            bundle.getString("reposit").toString()
        )

    }

    private fun initAdapter() {
        listpull.adapter = adapter
        var listrepo: List<Repo>

        viewModel.repoResult.observe(this) { result ->
            when (result) {
                is PullSearchResult.Success -> {
                    adapter.submitList(result.data.distinct())
                }
                is PullSearchResult.Error -> {
                    Toast.makeText(
                        this,
                        "\uD83D\uDE28 Wooops $result.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun setupScrollListener() {
        listpull.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                viewModel.lidstScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }
    override fun getViewModel() = MainViewModel2::class.java
    override val layoutRes = R.layout.pull_activity
}