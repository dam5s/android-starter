package io.damo.androidstarter

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import io.damo.androidstarter.backend.JokeApi

interface AppComponent {
    val jokeApi: JokeApi
    val appPreferences: AppPreferences
    val viewModelFactory: ViewModelProvider.Factory
}

class DefaultAppComponent(app: Application) : AppComponent {
    override val jokeApi = JokeApi(BuildConfig.API_URL)
    override val appPreferences: AppPreferences = AppPreferences(app)
    override val viewModelFactory = ViewModelFactory(this)
}
