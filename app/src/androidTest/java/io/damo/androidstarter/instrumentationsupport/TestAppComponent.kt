package io.damo.androidstarter.instrumentationsupport

import android.app.Application
import io.damo.androidstarter.AppComponent
import io.damo.androidstarter.DefaultAppComponent
import io.damo.androidstarter.backend.JokeApi

class TestAppComponent(app: Application) : AppComponent by DefaultAppComponent() {

    val testDispatcher = TestDispatcher()

    private val mockServer = startMockServer(testDispatcher)
    private val baseUrl = mockServer.baseUrl()

    override val jokeApi = JokeApi(baseUrl)

    fun shutdownServer() {
        mockServer.shutdown()
    }
}
