package com.jlmcdeveloper.githubjavapop.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jlmcdeveloper.githubjavapop.data.api.ApiEndPoint
import com.jlmcdeveloper.githubjavapop.data.api.GithubDataSource
import com.jlmcdeveloper.githubjavapop.data.model.GitCollection
import com.jlmcdeveloper.githubjavapop.data.model.PullRequest
import com.jlmcdeveloper.githubjavapop.data.model.User
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class RepositoryPullRequestTest {
    private lateinit var pullRequest: RepositoryPullRequest
    private var passed = false
    @MockK private lateinit var githubDataSource: GithubDataSource

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        val collection = GitCollection("repo","",0,0,
            User("user","",""))

        pullRequest = RepositoryPullRequest(githubDataSource, collection)
        passed = false
    }




    @Test
    fun listPullRequest_success() {
        assert(pullRequest.pullRequests.isEmpty())

        var success: (MutableList<PullRequest>) -> Unit = {}
        every{ githubDataSource.listPullRequest("user", "repo", any(),any()) } answers {
            success = thirdArg()
        }


        // ---- buscar pagina um ------
        pullRequest.listPullRequest({
            assert(pullRequest.pullRequests.size == ApiEndPoint.perPage)
            assert(it[0].title == "title0")
            passed = true
        },{
            fail()
        })


        val pR = mutableListOf<PullRequest>()
        for( i in 0 until ApiEndPoint.perPage)
            pR.add(PullRequest("title$i","body$i", "url$i","state$i", User()))

        success(pR)
        assert(passed)
    }
}
