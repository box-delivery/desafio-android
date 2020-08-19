package com.jlmcdeveloper.githubjavapop.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.jlmcdeveloper.githubjavapop.R
import com.jlmcdeveloper.githubjavapop.databinding.ActivityMainBinding
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel  by inject()
    private lateinit var adapter: RepositoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        adapter = RepositoryAdapter(viewModel, this)
         binding.recyclerView.adapter = adapter




        //------ posição da lista para o carregamento automatico --------
        binding.recyclerView.addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                (binding.recyclerView.layoutManager as LinearLayoutManager?)?.let {
                    viewModel.positionList(it.findLastVisibleItemPosition())
                }
            }
        })


        // ----------notify hole------------
        viewModel.loadingList.observe(this, Observer {
            adapter.notifyDataSetChanged()
        })


        // ----- mensagem de erro -------
        viewModel.message.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

    }

    override fun onStart() {
        super.onStart()
        viewModel.load()
    }
}