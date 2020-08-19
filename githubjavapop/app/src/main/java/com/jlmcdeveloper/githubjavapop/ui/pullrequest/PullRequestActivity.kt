package com.jlmcdeveloper.githubjavapop.ui.pullrequest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jlmcdeveloper.githubjavapop.R
import com.jlmcdeveloper.githubjavapop.databinding.ActivityPullRequestBinding
import com.jlmcdeveloper.githubjavapop.ui.main.RepositoryAdapter
import org.koin.android.ext.android.inject

class PullRequestActivity : AppCompatActivity() {
    private val viewModel: PullRequestViewModel by inject()
    private lateinit var adapter: PullRequestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityPullRequestBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_pull_request)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setSupportActionBar(binding.toolbarPullRequest)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = PullRequestAdapter(viewModel, this)
        binding.recyclerViewPullRequest.adapter = adapter



        // ----- mensagem de erro -------
        viewModel.message.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })

    }

    override fun onStart() {
        super.onStart()
        viewModel.load()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }
}