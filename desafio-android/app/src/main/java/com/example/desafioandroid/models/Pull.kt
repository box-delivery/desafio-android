package com.example.desafioandroid.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Pull(
    val id: Int,
    val html_url: String,
    val user: User,
    val title: String,
    val body: String

) : Parcelable