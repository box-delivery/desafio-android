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
import junit.framework.TestCase
import org.junit.Test

import org.junit.Before
import org.junit.Rule

class RepositoryMainTest {

    private lateinit var repositoryMain: RepositoryMain
    private var passed = false
    @MockK
    private lateinit var githubDataSource: GithubDataSource

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        val collection = GitCollection()
        repositoryMain = RepositoryMain(githubDataSource, collection)
        passed = false
    }



    @Test
    fun moreCollection_successPageOne() {
        assert(repositoryMain.gitCollection.isEmpty())

        var success: (MutableList<GitCollection>) -> Unit = {}
        every{ githubDataSource.listRepository(1, ApiEndPoint.language, any(),any()) } answers {
            success = thirdArg()
        }


        // ---- buscar pagina um ------
        repositoryMain.moreCollection({
            assert(repositoryMain.gitCollection.size == ApiEndPoint.perPage)
            assert(it[0].title == "title0")
            passed = true
        },{
            TestCase.fail()
        })


        val gC = mutableListOf<GitCollection>()
        for( i in 0 until ApiEndPoint.perPage)
            gC.add(GitCollection("title$i","body$i", i, i, User()))

        success(gC)

        assert(passed)
    }


    @Test
    fun moreCollection_successMultiplePage() {
        moreCollection_successPageOne()


        var success: (MutableList<GitCollection>) -> Unit = {}
        every{ githubDataSource.listRepository(2, ApiEndPoint.language, any(),any()) } answers {
            success = thirdArg()
        }


        // ---- buscar pagina um ------
        repositoryMain.moreCollection({
            assert(repositoryMain.gitCollection.size == ApiEndPoint.perPage*2)
            assert(it[ApiEndPoint.perPage+1].title == "title${ApiEndPoint.perPage+1}")
            passed = true
        },{
            TestCase.fail()
        })

        val gC = mutableListOf<GitCollection>()
        for( i in ApiEndPoint.perPage until ApiEndPoint.perPage*2)
            gC.add(GitCollection("title$i","body$i", i, i, User()))

        success(gC)
        assert(passed)
    }
}