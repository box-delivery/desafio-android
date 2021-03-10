package com.example.desafioandroid.activitys

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.desafioandroid.R
import com.example.desafioandroid.adapters.RepositoriosAdapter
import com.example.desafioandroid.api.RetrofitClient
import com.example.desafioandroid.models.responses.RepositoriosResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var adapter: RepositoriosAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var page = 1
    private var isLoading = false
    private var totalPage = 15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        layoutManager = LinearLayoutManager(this)
        swipeRefresh.setOnRefreshListener(this)
        setupRecyclerView()
        getRepositories(false)
        rvRepo.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = adapter.itemCount
                if (!isLoading && page < totalPage) {
                    if (visibleItemCount + pastVisibleItem >= total) {
                        page++
                        getRepositories(false)
                    }
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })

    }

    private fun getRepositories(isOnRefresh: Boolean) {
        isLoading = true
        if (!isOnRefresh) progressBar.visibility = View.VISIBLE
        val parameters = HashMap<String, String>()
        parameters["page"] = page.toString()
        RetrofitClient.instance.getRepositories(parameters).enqueue(object : Callback<RepositoriosResponse> {
            override fun onResponse(
                    call: Call<RepositoriosResponse>,
                    response: Response<RepositoriosResponse>
            ) {
                val listResponse = response.body()?.items
                if (listResponse != null) {
                    adapter.addList(listResponse)
                }
                progressBar.visibility = View.INVISIBLE
                isLoading = false
                swipeRefresh.isRefreshing = false
            }

            override fun onFailure(call: Call<RepositoriosResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.message}", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun setupRecyclerView() {
        rvRepo.setHasFixedSize(true)
        rvRepo.layoutManager = layoutManager
        adapter = RepositoriosAdapter()
        rvRepo.adapter = adapter
    }

    override fun onRefresh() {
        adapter.clear()
        page = 1
        getRepositories(true)
    }



}