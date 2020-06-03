package io.damo.androidstarter.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.damo.androidstarter.backend.JokeApi
import io.damo.androidstarter.randomjoke.JokeView
import io.damo.androidstarter.support.LiveRemoteData
import io.damo.androidstarter.support.MutableLiveRemoteData
import io.damo.androidstarter.support.Result
import io.damo.androidstarter.support.createLiveRemoteData
import io.damo.androidstarter.support.loadWith
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.ConcurrentHashMap

class CategoryJokesViewModel(
    private val jokeApi: JokeApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val jokes = ConcurrentHashMap<String, MutableLiveRemoteData<List<JokeView>>>()

    fun jokes(categoryName: String): LiveRemoteData<List<JokeView>> =
        findOrCreate(categoryName)

    fun loadCategory(categoryName: String) =
        viewModelScope.launch {
            findOrCreate(categoryName).loadWith { fetchJokes(categoryName) }
        }

    private suspend fun fetchJokes(categoryName: String): Result<List<JokeView>> =
        withContext(ioDispatcher) {
            jokeApi
                .getJokesForCategory(categoryName)
                .map { jokes ->
                    jokes.map { JokeView(it.joke) }
                }
        }

    private fun findOrCreate(categoryName: String): MutableLiveRemoteData<List<JokeView>> {
        jokes.putIfAbsent(categoryName, createLiveRemoteData())
        return jokes[categoryName]!!
    }
}
