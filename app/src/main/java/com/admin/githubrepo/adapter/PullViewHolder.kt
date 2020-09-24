package com.admin.githubrepo.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.admin.githubrepo.R
import com.admin.githubrepo.model.Pull
import com.admin.githubrepo.model.Repo
import com.squareup.picasso.Picasso

/**
 * View Holder for a [Repo] RecyclerView list item.
 */
class PullViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.pullname)
    private val description: TextView = view.findViewById(R.id.pullbody)

    private val loginname: TextView = view.findViewById(R.id.textv_loginname)
    private var urlavatar: String? = null
    private val fullname: TextView = view.findViewById(R.id.textv_fullname)
    private val imageView: ImageView = view.findViewById(R.id.imageView)

    private var repo: Pull? = null


    init {

       getclick()

    }

    fun getclick(){

        itemView.setOnClickListener {

        }
    }


    fun bind(repo: Pull, cellClickListener: CellClickListene) {

        if (repo == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
            description.visibility = View.GONE
            loginname.visibility = View.GONE
            fullname.visibility = View.GONE
            imageView.visibility = View.GONE
        } else {
            showRepoData(repo, cellClickListener)

        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun showRepoData(repo: Pull, cellClickListener: CellClickListene) {
        this.repo = repo
        name.text = repo.title

        // if the description is missing, hide the TextView
        var descriptionVisibility = View.GONE
        if (repo.title != null) {
            description.text = repo.body
            descriptionVisibility = View.VISIBLE
        }
        description.visibility = descriptionVisibility


        loginname.text = repo.owner.loginname
        urlavatar = repo.owner.avatar_url
        fullname.text = name.text
        Picasso.get().load(urlavatar).into(imageView)

        itemView.setOnClickListener {

            cellClickListener.onCellClickListener2(repo)
        }
    }

    companion object {
        fun create(parent: ViewGroup): PullViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.pull_view_item, parent, false)
            return PullViewHolder(view)
        }
    }
}
