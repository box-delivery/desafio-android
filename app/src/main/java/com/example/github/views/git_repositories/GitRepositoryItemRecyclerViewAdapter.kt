package com.example.github.views.git_repositories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.github.R
import com.example.github.databinding.ItemGitRepositoryBinding
import com.example.github.models.GitRepository


class GitRepositoryItemRecyclerViewAdapter(private val dataBindingComponent: DataBindingComponent) : ListAdapter<GitRepository, GitRepositoryItemRecyclerViewAdapter.RepositoriesViewHolder>(GitRepositoriesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriesViewHolder {
        return RepositoriesViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_git_repository,
                parent,
                false,
                dataBindingComponent
            )
        )
    }

    override fun onBindViewHolder(holder: RepositoriesViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }


    inner class RepositoriesViewHolder(private val binding: ItemGitRepositoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GitRepository) {
            binding.apply {
                repository = item
                root.setOnClickListener(
                    Navigation.createNavigateOnClickListener(
                        RepositoriesFragmentDirections.actionNavigationReposToNavigationPullRequests(
                            item.owner.login,
                            item.repoName)
                    )
                )
                executePendingBindings()
            }
        }


    }
}

private class GitRepositoriesDiffCallback: DiffUtil.ItemCallback<GitRepository>() {
    override fun areItemsTheSame(oldItem: GitRepository, newItem: GitRepository): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: GitRepository, newItem: GitRepository): Boolean = oldItem == newItem

}