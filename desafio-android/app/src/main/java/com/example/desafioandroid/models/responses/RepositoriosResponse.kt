package com.example.desafioandroid.models.responses

import com.example.desafioandroid.models.Repositorio

data class RepositoriosResponse(
    val total_count: Int,
    val items: ArrayList<Repositorio>
)