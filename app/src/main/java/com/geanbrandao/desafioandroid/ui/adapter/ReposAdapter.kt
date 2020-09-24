package com.geanbrandao.desafioandroid.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geanbrandao.desafioandroid.R
import com.geanbrandao.desafioandroid.data.repositories.Item
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_repos.view.*

class ReposAdapter(
    private val context: Context,
    private val onClick: ((item: Item) -> Unit),
    private val data: ArrayList<Item> = arrayListOf()
) : RecyclerView.Adapter<ReposAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_repos, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data[position]

        holder.bindView(item)

        holder.itemView.setOnClickListener {
            onClick.invoke(item)
        }
    }

    override fun getItemCount() = data.count()

    fun add(item: Item) {
        val oldSize = data.size
        data.add(item)
        val newSize = data.size
        notifyItemRangeChanged(oldSize, newSize)
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val reposTitle = itemView.text_repos_title as AppCompatTextView
        val reposDescription = itemView.text_repos_description as AppCompatTextView
        val forks = itemView.text_forks as AppCompatTextView
        val stars = itemView.text_stars as AppCompatTextView
        val profile = itemView.image_profile as CircleImageView
        val username = itemView.text_username as AppCompatTextView
        val name = itemView.text_name as AppCompatTextView


        fun bindView(item: Item) {
            reposTitle.text = item.name
            reposDescription.text = item.description
            forks.text = item.forks.toString()
            stars.text = item.stargazers_count.toString()

            username.text = item.owner.login
//            name.text

            Glide.with(itemView.context)
                    .load(item.owner.avatar_url)
                    .error(R.drawable.ic_placeholder_user)
                    .placeholder(R.drawable.ic_placeholder_user)
                    .into(profile)


        }
    }
}