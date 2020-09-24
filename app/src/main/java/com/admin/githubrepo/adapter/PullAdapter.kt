package com.admin.githubrepo.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.admin.githubrepo.model.Pull


/**
 * Adapter for the list of repositories.
 */
class PullAdapter(private val cellClickListener: CellClickListene) : ListAdapter<
        Pull,
        androidx.recyclerview.widget.RecyclerView.ViewHolder>
    (
         REPO_COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return PullViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)

        if (repoItem != null) {
            (holder as PullViewHolder).bind(repoItem,cellClickListener)
        }else{

        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Pull>() {
            override fun areItemsTheSame(oldItem: Pull, newItem: Pull): Boolean =
                    oldItem.title == newItem.title

            override fun areContentsTheSame(oldItem: Pull, newItem: Pull): Boolean =
                    oldItem == newItem
        }
    }
}
