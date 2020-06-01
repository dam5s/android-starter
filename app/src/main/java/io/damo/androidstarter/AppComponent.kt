package io.damo.androidstarter

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

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

class ViewModelFactory(private val appComponent: AppComponent) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when (modelClass) {
            RandomJokeViewModel::class.java -> RandomJokeViewModel(
                appComponent.jokeApi,
                appComponent.appPreferences
            ) as T

            else -> throw IllegalArgumentException("Unexpected view model class $modelClass")
        }
}
