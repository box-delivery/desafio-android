package com.geanbrandao.desafioandroid.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.geanbrandao.desafioandroid.R
import com.geanbrandao.desafioandroid.data.pull_request.Item

class PullRequestAdapter(
    private val context: Context,
    private val data: ArrayList<Item> = arrayListOf()
): RecyclerView.Adapter<PullRequestAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_pull_request, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

    }

    override fun getItemCount() = data.count()


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bindView() {

        }
    }
}