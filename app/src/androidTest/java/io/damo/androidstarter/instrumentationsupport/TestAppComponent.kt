package io.damo.androidstarter.instrumentationsupport

import android.app.Application
import io.damo.androidstarter.AppComponent
import io.damo.androidstarter.DefaultAppComponent
import io.damo.androidstarter.JokeApi
import io.damo.androidstarter.ViewModelFactory

class TestAppComponent(app: Application) : AppComponent by DefaultAppComponent(app) {

    private val mockServer = startMockServer(TestDispatcher())
    private val baseUrl = mockServer.baseUrl()

    override val jokeApi = JokeApi(baseUrl)
    override val viewModelFactory = ViewModelFactory(this)
}
