package io.damo.androidstarter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.damo.androidstarter.support.*
import io.damo.androidstarter.support.RemoteData.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RandomJokeViewModel(app: Application) : AndroidViewModel(app) {

    private val jokeApi: JokeApi = app.appComponent.jokeApi
    private val joke: MutableLiveRemoteData<JokeView> = createLiveRemoteData()

    fun joke(): LiveRemoteData<JokeView> = joke

    fun loadJoke() {
        viewModelScope.launch {
            if (joke.value == null) {
                joke.value = Loading()
            }

            joke.value = fetchJoke()
        }
    }

    private suspend fun fetchJoke() =
        withContext(Dispatchers.IO) {
            jokeApi
                .getRandomJoke()
                .map { JokeView(it.joke) }
                .toRemoteData()
        }
}

data class JokeView(val content: String)
