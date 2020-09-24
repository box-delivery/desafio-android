package com.geanbrandao.desafioandroid.data.pull_request

data class PullRequestResponse(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int,
    var closed: Int,
    var open: Int
)