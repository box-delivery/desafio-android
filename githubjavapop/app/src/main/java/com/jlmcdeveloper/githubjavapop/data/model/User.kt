package com.jlmcdeveloper.githubjavapop.data.model

import android.widget.ImageView
import com.jlmcdeveloper.githubjavapop.data.api.PullRequestResponse
import com.jlmcdeveloper.githubjavapop.data.api.RepositoryResponse
import com.squareup.picasso.Picasso

data class User(val name: String,
                val infoUser: String,
                val photo: String){



    constructor(owner : RepositoryResponse.Owner, fullName: String) : this(
        name = owner.login,
        infoUser = fullName,
        photo = owner.avatar_url
    )


    constructor(user : PullRequestResponse.User, infoUser: String) : this(
        name = user.login,
        infoUser = infoUser,
        photo = user.avatar_url
    )


    fun setImage(imageView: ImageView) {
        Picasso.get()
            .load(photo)
            .into(imageView)
    }
}