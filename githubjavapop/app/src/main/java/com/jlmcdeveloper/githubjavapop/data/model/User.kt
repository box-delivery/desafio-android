package com.jlmcdeveloper.githubjavapop.data.model

import android.widget.ImageView
import com.jlmcdeveloper.githubjavapop.data.api.PullRequestResponse
import com.jlmcdeveloper.githubjavapop.data.api.RepositoryListResponse
import com.squareup.picasso.Picasso

data class User(val name: String,
                val updatedDate: String,
                val photo: String){



    constructor(owner : RepositoryListResponse.Owner, fullName: String) : this(
        name = owner.login,
        updatedDate = fullName,
        photo = owner.avatarUrl
    )


    constructor(user : PullRequestResponse.User, updatedDate: String) : this(
        name = user.login,
        updatedDate = updatedDate,
        photo = user.avatarUrl
    )


    fun setImage(imageView: ImageView) {
        Picasso.get()
            .load(photo)
            .into(imageView)
    }
}