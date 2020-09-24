package com.geanbrandao.desafioandroid.data.pull_request

data class Label(
    val color: String,
    val default: Boolean,
    val description: Any,
    val id: Int,
    val name: String,
    val node_id: String,
    val url: String
)