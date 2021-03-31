package io.damo.androidstarter

import android.app.Application
import io.damo.androidstarter.backend.JokeApi

interface AppComponent {
    val jokeApi: JokeApi
    val initialState: AppLifeCycle.State
}

class DefaultAppComponent(app: Application) : AppComponent {
    override val jokeApi = JokeApi(BuildConfig.API_URL)
    override val initialState = AppLifeCycle.State()
}
