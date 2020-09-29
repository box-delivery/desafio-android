package com.example.github.views.pullrequests

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.github.R
import com.example.github.binding.FragmentDataBindingComponent
import com.example.github.databinding.FragmentPullRequestsBinding
import com.example.github.di.AppModule
import com.example.github.util.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class PullRequestsFragment : Fragment() {

    @AppModule.PullRequestViewModelFactoryInj
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<PullRequestsViewModel> {
        viewModelFactory
    }

    private val args: PullRequestsFragmentArgs by navArgs()

    private lateinit var binding: FragmentPullRequestsBinding

    private lateinit var adapter: PullRequestItemRecyclerViewAdapter

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadRepoPullRequests(args.ownerLogin, args.repoName)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentPullRequestsBinding>(inflater, R.layout.fragment_pull_requests,container, false)
        binding = dataBinding
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = PullRequestItemRecyclerViewAdapter(dataBindingComponent)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.list.adapter = adapter
        initRecyclerView()
    }

    private fun initRecyclerView() {
        viewModel.results.observe(viewLifecycleOwner, { result -> adapter.submitList(result?.data) })
    }

    companion object {

        val TAG: String = this::class.java.simpleName

        const val repoNameBundleKey: String = "repo_name"
        const val userLoginBundleKey: String = "user_login"
    }
}