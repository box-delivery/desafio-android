
package com.admin.githubrepo.api
import com.admin.githubrepo.model.Pull
import com.google.gson.annotations.SerializedName

/**
 * Data class to hold repo responses from searchRepo API calls.
 */
data class PullSearchResponse(
    @SerializedName("state") val total: String = "",
    @SerializedName("user") val items: Pull.User? = null,
    val nextPage: Int? = null
)
