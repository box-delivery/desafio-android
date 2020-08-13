package com.jlmcdeveloper.githubjavapop.ui.pullrequest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.jlmcdeveloper.githubjavapop.R
import com.jlmcdeveloper.githubjavapop.databinding.ActivityPullRequestBinding
import org.koin.android.ext.android.inject

class PullRequestActivity : AppCompatActivity() {
    private val viewModel: PullRequestViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityPullRequestBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_pull_request)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }
}