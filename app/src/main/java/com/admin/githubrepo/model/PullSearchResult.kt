
package com.admin.githubrepo.model
import java.lang.Exception

/**
 * RepoSearchResult from a search, which contains List<Repo> holding query data,
 * and a String of network error state.
 */
sealed class PullSearchResult {
    data class Success(val data: List<Pull>) : PullSearchResult()
    data class Error(val error: Exception) : PullSearchResult()
}
