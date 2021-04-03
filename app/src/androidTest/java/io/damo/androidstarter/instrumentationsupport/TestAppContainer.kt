package io.damo.androidstarter.instrumentationsupport

import android.app.Application
import io.damo.androidstarter.AppContainer
import io.damo.androidstarter.DefaultAppContainer
import io.damo.androidstarter.backend.JokeApi

class TestAppContainer(app: Application) : AppContainer by DefaultAppContainer() {

    val testDispatcher = TestDispatcher()

    private val mockServer = startMockServer(testDispatcher)
    private val baseUrl = mockServer.baseUrl()

    override val jokeApi = JokeApi(baseUrl)

    fun shutdownServer() {
        mockServer.shutdown()
    }
}
