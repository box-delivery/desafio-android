package com.jlmcdeveloper.githubjavapop.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.jlmcdeveloper.githubjavapop.data.model.GitCollection
import com.jlmcdeveloper.githubjavapop.databinding.ItemEmptyBinding
import com.jlmcdeveloper.githubjavapop.databinding.ItemLoadingBinding
import com.jlmcdeveloper.githubjavapop.databinding.ItemRepositoryBinding
import com.jlmcdeveloper.githubjavapop.ui.baseholder.BaseViewHolder
import com.jlmcdeveloper.githubjavapop.ui.baseholder.EmptyHolder
import com.jlmcdeveloper.githubjavapop.ui.baseholder.LoadingHolder
import com.jlmcdeveloper.githubjavapop.ui.pullrequest.PullRequestActivity

class RepositoryAdapter(
    private val viewModel: MainViewModel,
    owner: LifecycleOwner
) : RecyclerView.Adapter<BaseViewHolder<GitCollection>>() {

    private val VIEW_TYPE_EMPTY = 0
    private val VIEW_TYPE_NORMAL = 1
    private val VIEW_TYPE_ITEM_LOADING = 2

    private var listData: MutableList<GitCollection> = mutableListOf()

    init{
        viewModel.listData.observe(owner, Observer{
            if(it.isNotEmpty()) {
                listData = it
                notifyDataSetChanged()
            }
        })
        viewModel.loadingNewItems.observe(owner, Observer {
            if(!it)
                notifyItemRemoved(listData.size)
        })
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<GitCollection> {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        return when(viewType){
            VIEW_TYPE_NORMAL -> RepositoryHolder(
                { viewModel.setRepository(it) }, context, ItemRepositoryBinding.inflate(inflater, parent, false))

            VIEW_TYPE_ITEM_LOADING -> LoadingHolder(ItemLoadingBinding.inflate(inflater, parent, false))

            else -> EmptyHolder(
                { viewModel.updateList() }, ItemEmptyBinding.inflate(inflater, parent, false))
        }
    }

    override fun getItemCount(): Int {
        if(viewModel.loadingList.value!! || listData.isNotEmpty())
            return (listData.size + if(viewModel.loadingNewItems.value!!) 1 else 0)
        return 1    // item ViewHolder empty
    }


    override fun getItemViewType(position: Int): Int {
        if(viewModel.loadingList.value!! || listData.isNotEmpty()) {
            return if (listData.size > position)
                VIEW_TYPE_NORMAL
            else
                VIEW_TYPE_ITEM_LOADING
        }
        return VIEW_TYPE_EMPTY
    }

    override fun onBindViewHolder(holder: BaseViewHolder<GitCollection>, position: Int) {
        if(listData.size <= position || !viewModel.loadingList.value!! && listData.isEmpty())
            holder.bind(null)
        else
            holder.bind(listData[position])
    }




    //====================== ViewHolder Repository =============================
    class RepositoryHolder(
        private val setRepository: (GitCollection)-> Unit,
        private val context: Context,
        private val binding: ItemRepositoryBinding) : BaseViewHolder<GitCollection>(binding.root) {

        override fun bind(item: GitCollection?) {
            binding.repository = item
            item?.user!!.setImage(binding.imageUser)
            binding.executePendingBindings()

            // ----- abrir activity -----
            binding.cardViewRepository.setOnClickListener {
                setRepository(item)
                context.startActivity(Intent(context, PullRequestActivity::class.java))
            }
        }
    }
}