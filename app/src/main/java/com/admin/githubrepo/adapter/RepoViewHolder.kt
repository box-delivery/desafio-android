package com.admin.githubrepo.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.admin.githubrepo.R
import com.admin.githubrepo.model.Repo
import com.squareup.picasso.Picasso

/**
 * View Holder for a [Repo] RecyclerView list item.
 */
class RepoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.pullname)
    private val description: TextView = view.findViewById(R.id.pullbody)
    private val stars: TextView = view.findViewById(R.id.repo_stars)
    private val language: TextView = view.findViewById(R.id.repo_language)
    private val forks: TextView = view.findViewById(R.id.repo_forks)

    private val loginname: TextView = view.findViewById(R.id.textv_loginname)
    private var urlavatar: String? = null
    private val fullname: TextView = view.findViewById(R.id.textv_fullname)
    private val imageView: ImageView = view.findViewById(R.id.imageView)

    private var repo: Repo? = null


    init {

       getclick()

    }

    fun getclick(){

        itemView.setOnClickListener {

        }
    }


    fun bind(repo: Repo, cellClickListener: CellClickListene) {

        if (repo == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
            description.visibility = View.GONE
            language.visibility = View.GONE
            stars.text = resources.getString(R.string.unknown)
            forks.text = resources.getString(R.string.unknown)

            loginname.visibility = View.GONE
            fullname.visibility = View.GONE
            imageView.visibility = View.GONE
        } else {
            showRepoData(repo, cellClickListener)

        }
    }

    @SuppressLint("StringFormatInvalid")
    private fun showRepoData(repo: Repo, cellClickListener: CellClickListene) {
        this.repo = repo
        name.text = repo.fullName

        // if the description is missing, hide the TextView
        var descriptionVisibility = View.GONE
        if (repo.description != null) {
            description.text = repo.description
            descriptionVisibility = View.VISIBLE
        }
        description.visibility = descriptionVisibility

        stars.text = repo.stars.toString()
        forks.text = repo.forks.toString()

        // if the language is missing, hide the label and the value
        var languageVisibility = View.GONE
        if (!repo.language.isNullOrEmpty()) {
            val resources = this.itemView.context.resources
            language.text = resources.getString(R.string.language, repo.language)
            languageVisibility = View.VISIBLE
        }
        language.visibility = languageVisibility

        loginname.text = repo.owner.loginname
        urlavatar = repo.owner.avatar_url
        fullname.text = name.text
        Picasso.get().load(urlavatar).into(imageView)

        itemView.setOnClickListener {

            cellClickListener.onCellClickListener(repo)
        }
    }

    companion object {
        fun create(parent: ViewGroup): RepoViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.repo_view_item, parent, false)
            return RepoViewHolder(view)
        }
    }
}
