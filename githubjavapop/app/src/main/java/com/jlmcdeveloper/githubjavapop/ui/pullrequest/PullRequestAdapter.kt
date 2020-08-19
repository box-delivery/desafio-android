package com.jlmcdeveloper.githubjavapop.ui.pullrequest


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.jlmcdeveloper.githubjavapop.data.model.PullRequest
import com.jlmcdeveloper.githubjavapop.databinding.*
import com.jlmcdeveloper.githubjavapop.ui.baseholder.BaseViewHolder
import com.jlmcdeveloper.githubjavapop.ui.baseholder.EmptyHolder

class PullRequestAdapter(
    private val viewModel: PullRequestViewModel,
    owner: LifecycleOwner
) : RecyclerView.Adapter<BaseViewHolder<PullRequest>>() {

    private val VIEW_TYPE_EMPTY = 0
    private val VIEW_TYPE_NORMAL = 1

    private var listData: MutableList<PullRequest> = mutableListOf()

    init{
        viewModel.listData.observe(owner, Observer{
            if(it.isNotEmpty()) {
                listData = it
                notifyDataSetChanged()
            }
        })
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<PullRequest> {
        val context = parent.context
        val inflater = LayoutInflater.from(context)

        return when(viewType){
            VIEW_TYPE_NORMAL -> PullRequestHolder(
                context, ItemPullRequestBinding.inflate(inflater, parent, false))

            else -> EmptyHolder(
                { viewModel.updateList() }, ItemEmptyBinding.inflate(inflater, parent, false))
        }
    }

    override fun getItemCount(): Int {
        if(viewModel.loadingList.value!! || listData.isNotEmpty())
            return listData.size
        return 1    // item ViewHolder empty
    }


    override fun getItemViewType(position: Int): Int {
        if(viewModel.loadingList.value!! || listData.isNotEmpty()) {
            return VIEW_TYPE_NORMAL
        }
        return VIEW_TYPE_EMPTY
    }

    override fun onBindViewHolder(holder: BaseViewHolder<PullRequest>, position: Int) {
        if(!viewModel.loadingList.value!! && listData.isEmpty())
            holder.bind(null)
        else
            holder.bind(listData[position])
    }




    //====================== ViewHolder PullRequest =============================
    class PullRequestHolder(
        private val context: Context,
        private val binding: ItemPullRequestBinding
    ) : BaseViewHolder<PullRequest>(binding.root) {

        override fun bind(item: PullRequest?) {
            binding.pullRequest = item
            item?.user!!.setImage(binding.imageViewUser)
            binding.executePendingBindings()

            // ----- abrir activity -----
            binding.cardViewPullRequest.setOnClickListener {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.url)))
            }
        }
    }
}