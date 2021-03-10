package com.example.desafioandroid.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Repositorio(
    val id: Int,
    val name: String,
    val full_name: String,
    val description: String,
    val forks: Int,
    val watchers: Int,
    val owner: User
): Parcelable