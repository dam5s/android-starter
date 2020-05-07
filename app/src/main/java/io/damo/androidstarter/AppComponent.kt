package io.damo.androidstarter

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

interface AppComponent {
    val jokeApi: JokeApi
    val viewModelFactory: ViewModelProvider.Factory
}

class DefaultAppComponent(app: Application) : AppComponent, ViewModelProvider.Factory {

    override val jokeApi = JokeApi(BuildConfig.API_URL)
    override val viewModelFactory = this

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when (modelClass) {
            RandomJokeViewModel::class -> RandomJokeViewModel(jokeApi) as T
            else -> throw IllegalArgumentException("Unexpected view model class $modelClass")
        }
}
