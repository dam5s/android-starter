package io.damo.androidstarter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.damo.androidstarter.categories.CategoryJokesViewModel
import io.damo.androidstarter.randomjoke.RandomJokeViewModel

class ViewModelFactory(private val appComponent: AppComponent) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        when (modelClass) {
            RandomJokeViewModel::class.java ->
                RandomJokeViewModel(appComponent.jokeApi, appComponent.appPreferences) as T

            CategoryJokesViewModel::class.java ->
                CategoryJokesViewModel(appComponent.jokeApi) as T

            else ->
                throw IllegalArgumentException("Unexpected view model class $modelClass")
        }
}
