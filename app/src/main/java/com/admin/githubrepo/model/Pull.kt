
package com.admin.githubrepo.model

import com.google.gson.annotations.SerializedName

/**
 * Immutable model class for a Github repo that holds all the information about a repository.
 * Objects of this type are received from the Github API, therefore all the fields are annotated
 * with the serialized name.
 * This class also defines the Room repos table, where the repo [id] is the primary key.
 */
data class Pull(
    @field:SerializedName("id") val id: Long,
    @field:SerializedName("state") val state: String,
    @field:SerializedName("title") val title: String,
    @field:SerializedName("body") val body: String,
    @field:SerializedName("user") val owner: User,
    @field:SerializedName("number") val number: Long
){
    data class User(

        @field:SerializedName("avatar_url") val avatar_url: String?,
        @field:SerializedName("login") val loginname: String?
    )
}
