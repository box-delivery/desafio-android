package com.geanbrandao.desafioandroid.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.geanbrandao.desafioandroid.R
import com.geanbrandao.desafioandroid.data.pull_request.Item
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_pull_request.view.*

class PullRequestAdapter(
    private val context: Context,
    private val onClick: ((item: Item) -> Unit),
    private val data: ArrayList<Item> = arrayListOf()
): RecyclerView.Adapter<PullRequestAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_pull_request, parent, false)

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

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val pullTitle = itemView.text_pull_title as AppCompatTextView
        val pullDescription = itemView.text_pull_description as AppCompatTextView
        val profile = itemView.image_profile as CircleImageView
        val username = itemView.text_username as AppCompatTextView
        val name = itemView.text_name as AppCompatTextView

        fun bindView(item: Item) {
            pullTitle.text = item.title
            pullDescription.text = item.body

            username.text = item.user.login

            Glide.with(itemView.context)
                .load(item.user.avatar_url)
                .error(R.drawable.ic_placeholder_user)
                .placeholder(R.drawable.ic_placeholder_user)
                .into(profile)
        }
    }
}