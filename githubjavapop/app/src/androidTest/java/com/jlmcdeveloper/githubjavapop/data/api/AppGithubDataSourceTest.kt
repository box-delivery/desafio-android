package com.jlmcdeveloper.githubjavapop.data.api

import androidx.test.core.app.ApplicationProvider
import com.jlmcdeveloper.githubjavapop.di.appModulesTest
import okhttp3.mockwebserver.MockWebServer
import org.awaitility.Awaitility
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.inject
import org.koin.test.KoinTest


class AppGithubDataSourceTest : KoinTest {

    private val mockWebServer: MockWebServer by inject()
    private val githubDataSource: AppGithubDataSource by inject()
    private var wait = false

    @Before
    fun setUp() {
        wait = false
        stopKoin()
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(appModulesTest)
        }

        mockWebServer.dispatcher = AppDispatcher()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        stopKoin()
        mockWebServer.shutdown()
    }



    @Test
    fun listRepositoryTest_success(){

        githubDataSource.listRepository(1, language= ApiEndPoint.language, success= {
            assertEquals(it.size, AppDispatcher.size)
            val repository = it[0]
            assertEquals(repository.title, AppDispatcher.name)
            assertEquals(repository.description, AppDispatcher.description)
            assertEquals(repository.fork, AppDispatcher.forks_count.toInt())
            assertEquals(repository.star, AppDispatcher.stargazers_count.toInt())
            assertEquals(repository.user.name, AppDispatcher.owner.login)
            assertEquals(repository.user.photo, AppDispatcher.owner.avatarUrl)
            wait = true
        }, failure= {
            fail("não poderia falhar aqui, tipo do erro: $it")
            wait = true
        })
        Awaitility.await().until{wait}

    }



    @Test
    fun listRepositoryTest_failureBodyNull(){
        githubDataSource.listRepository(2, language = ApiEndPoint.language, success = {
            fail()
            wait = true
        }, failure = {
            assertEquals(it, "response.body() = null")
            wait = true
        })
        Awaitility.await().until{wait}
    }

    @Test
    fun listRepositoryTest_failure(){
        githubDataSource.listRepository(1, language = "test", success = {
            fail()
            wait = true
        }, failure = {
            assert(it.contains("error: onFailure: ",true))
            wait = true
        })
        Awaitility.await().until{wait}
    }




    @Test
    fun listPullRequestTest_success(){

        githubDataSource.listPullRequest(1,
            userName = AppDispatcher.pull.user,
            repositoryName = AppDispatcher.pull.repository,
            success= {
            assertEquals(it.size, AppDispatcher.size)
            val pullRequest = it[0]
            assertEquals(pullRequest.title, AppDispatcher.pull.request.title)
            assertEquals(pullRequest.body, AppDispatcher.pull.request.body)
            assertEquals(pullRequest.url, AppDispatcher.pull.request.url)
            assertEquals(pullRequest.user.name, AppDispatcher.pull.request.user.name)
            assertEquals(pullRequest.user.photo, AppDispatcher.pull.request.user.photo)
            assertEquals(pullRequest.user.userInfo, AppDispatcher.pull.request.user.userInfo)

            wait = true
        }, failure= {
            fail("não poderia falhar aqui, tipo do erro: $it")
            wait = true
        })
        Awaitility.await().until{wait}
    }

    @Test
    fun listPullRequestTest_failure(){
        githubDataSource.listPullRequest(2,
            userName = AppDispatcher.pull.user,
            repositoryName = AppDispatcher.pull.repository,
            success= {
                fail()
                wait = true
            }, failure = {
                assertEquals(it, "response.body() = null")
                wait = true
            })
        Awaitility.await().until{wait}
    }
}