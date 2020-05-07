package io.damo.androidstarter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.damo.androidstarter.support.LiveRemoteData
import io.damo.androidstarter.support.createLiveRemoteData
import io.damo.androidstarter.support.hasNoValue
import io.damo.androidstarter.support.resolve
import io.damo.androidstarter.support.setLoading
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RandomJokeViewModel(
    private val jokeApi: JokeApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val joke = createLiveRemoteData<JokeView>()

    fun joke(): LiveRemoteData<JokeView> = joke

    fun loadJoke() {
        viewModelScope.launch {
            if (joke.hasNoValue()) {
                joke.setLoading()
            }

            joke.resolve(fetchJoke())
        }
    }

    private suspend fun fetchJoke() =
        withContext(ioDispatcher) {
            jokeApi
                .getRandomJoke()
                .map { JokeView(it.joke) }
        }
}

data class JokeView(val content: String)
