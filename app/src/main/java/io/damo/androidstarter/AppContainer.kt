package io.damo.androidstarter

import io.damo.androidstarter.backend.JokeApi

interface AppContainer {
    val jokeApi: JokeApi
    val initialState: AppLifeCycle.State
}

class DefaultAppContainer : AppContainer {
    override val jokeApi = JokeApi(BuildConfig.API_URL)
    override val initialState = AppLifeCycle.State()
}
