package com.example.github.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.github.models.PullRequest

@Dao
interface PullRequestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg pullRequest: PullRequest)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMany(pullRequest: List<PullRequest>)

    @Query("SELECT * FROM pull_requests")
    fun loadAll(): LiveData<PullRequest>

    @Query("SELECT * FROM pull_requests WHERE repo_name = :repoName")
    fun loadByRepositoryName(repoName: String): LiveData<List<PullRequest>>

}