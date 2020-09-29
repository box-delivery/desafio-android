package com.example.github.views.git_repositories

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.github.R
import com.example.github.binding.FragmentDataBindingComponent
import com.example.github.databinding.FragmentRepositoriesBinding
import com.example.github.di.AppModule
import com.example.github.util.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A fragment representing a list of Github Repositories.
 */
@AndroidEntryPoint
class RepositoriesFragment : Fragment() {
    @AppModule.GitRepositoryViewModelFactoryInj
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by viewModels<GitRepositoriesViewModel>() {
        viewModelFactory
    }
    lateinit var binding: FragmentRepositoriesBinding

    private lateinit var adapter: GitRepositoryItemRecyclerViewAdapter

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<FragmentRepositoriesBinding>(inflater,
        R.layout.fragment_repositories,
        container,
        false)
        binding = dataBinding
        viewModel.setQuery("language:Java")
        // Set the adapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = GitRepositoryItemRecyclerViewAdapter(dataBindingComponent)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.list.adapter = adapter
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == adapter.itemCount - 1) {
                    viewModel.loadNextPage()
                }
            }
        })
        viewModel.results.observe(viewLifecycleOwner, { result -> adapter.submitList(result?.data) })

    }

    companion object {

        val TAG: String = this::class.java.simpleName

    }
}