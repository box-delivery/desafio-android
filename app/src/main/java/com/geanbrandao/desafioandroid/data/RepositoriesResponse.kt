package com.geanbrandao.desafioandroid.data

import java.io.Serializable

data class RepositoriesResponse(
    val incomplete_results: Boolean,
    val items: ArrayList<Item>,
    val total_count: Int
): Serializable