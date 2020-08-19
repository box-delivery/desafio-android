package com.jlmcdeveloper.githubjavapop.data.model

import android.widget.ImageView
import com.jlmcdeveloper.githubjavapop.data.api.model.PullRequestResponse
import com.jlmcdeveloper.githubjavapop.data.api.model.RepositoryListResponse
import com.squareup.picasso.Picasso

data class User(var name: String,
                var userInfo: String,
                var photo: String){

    constructor(): this("","","")

    constructor(owner : RepositoryListResponse.Owner, fullName: String) : this(
        name = owner.login,
        userInfo = fullName,
        photo = owner.avatarUrl
    )


    constructor(user : PullRequestResponse.User, updatedDate: String) : this(
        name = user.login,
        userInfo = updatedDate,
        photo = user.avatarUrl
    )


    fun setImage(imageView: ImageView) {
        Picasso.get()
            .load(photo)
            .into(imageView)
    }
}