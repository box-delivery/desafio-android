package com.jlmcdeveloper.githubjavapop.data.api

object ApiEndPoint {
    const val url = "https://api.github.com"
    const val language = "Java"
    const val perPage = 5
    const val sort = "stars"



    fun queryString(vararg pair: Pair<String, String>): String{
        val query = mapOf(*pair)

        var result = ""
        query.forEach { result += "${it.key}:${it.value}+"}
        return result.trimEnd('+')
    }
}