package com.example.github.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.github.models.GitRepositoriesSearchResult
import com.example.github.models.GitRepository

@Dao
interface GitRepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGitRepos(vararg repos: GitRepository)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertManyGitRepos(repos: List<GitRepository>)

    @Query("SELECT * FROM repositories")
    fun loadAllGitRepos(): LiveData<List<GitRepository>>

    @Query("SELECT * FROM repositories WHERE id in (:ids) ORDER BY stars desc")
    fun loadGitReposById(ids: List<Int>): LiveData<List<GitRepository>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSearchResult(vararg searchResult: GitRepositoriesSearchResult)

    @Query("SELECT * FROM repo_search_result WHERE `query` = :query")
    fun searchLiveData(query: String): LiveData<GitRepositoriesSearchResult?>

    @Query("SELECT * FROM repo_search_result WHERE `query` = :query")
    abstract fun findSearchResult(query: String): GitRepositoriesSearchResult?
}