package com.geanbrandao.desafioandroid.data.pull_request

import java.io.Serializable

data class Item(
    val active_lock_reason: Any,
    val assignee: Any,
    val assignees: List<Any>,
    val author_association: String,
    val body: String,
    val closed_at: Any,
    val comments: Int,
    val comments_url: String,
    val created_at: String,
    val events_url: String,
    val html_url: String,
    val id: Int,
    val labels: List<Label>,
    val labels_url: String,
    val locked: Boolean,
    val milestone: Any,
    val node_id: String,
    val number: Int,
    val performed_via_github_app: Any,
    val repository_url: String,
    val score: Double,
    val state: String,
    val title: String,
    val updated_at: String,
    val url: String,
    val user: User
): Serializable