package com.geanbrandao.desafioandroid.data.pull_request

import java.io.Serializable

data class PullRequestResponse(
    val incomplete_results: Boolean,
    val items: ArrayList<Item>,
    val total_count: Int,
    var closed: Int,
    var open: Int
): Serializable