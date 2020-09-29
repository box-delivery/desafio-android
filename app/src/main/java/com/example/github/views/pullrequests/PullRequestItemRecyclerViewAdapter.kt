package com.example.github.views.pullrequests

import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.github.R
import com.example.github.binding.FragmentDataBindingComponent
import com.example.github.databinding.ItemPullRequestBinding

import com.example.github.models.PullRequest


class PullRequestItemRecyclerViewAdapter(
    private val dataBindingComponent: DataBindingComponent
) : ListAdapter<PullRequest, PullRequestItemRecyclerViewAdapter.PullRequestsViewHolder>(PullRequestsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestsViewHolder {
        return PullRequestsViewHolder(
            DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_pull_request,
                    parent,
                    false,
                    dataBindingComponent
                ))
    }

    override fun onBindViewHolder(holder: PullRequestsViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }


    inner class PullRequestsViewHolder(private val binding: ItemPullRequestBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PullRequest) {
            binding.apply {
                pullRequest = item
                root.setOnClickListener { view -> view.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.url))) }
                executePendingBindings()
            }
        }

    }
}

private class PullRequestsDiffCallback: DiffUtil.ItemCallback<PullRequest>() {
    override fun areItemsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PullRequest, newItem: PullRequest): Boolean = oldItem == newItem

}