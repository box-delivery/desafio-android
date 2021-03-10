package com.example.desafioandroid.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Int,
    val login: String,
    val avatar_url: String,
    val html_url: String,
    val repos_url: String,
    val name: String? = null
) : Parcelable