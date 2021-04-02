package io.damo.androidstarter

import io.damo.androidstarter.backend.JokeApi

interface AppComponent {
    val jokeApi: JokeApi
    val initialState: AppLifeCycle.State
}

class DefaultAppComponent : AppComponent {
    override val jokeApi = JokeApi(BuildConfig.API_URL)
    override val initialState = AppLifeCycle.State()
}
