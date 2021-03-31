package io.damo.androidstarter.backend

import io.damo.androidstarter.prelude.Async
import java.util.Locale

class JokeApi(private val apiUrl: String) {

    fun getRandomJoke(): HttpResult<JokeJson> =
        requestBuilder("$apiUrl/jokes/random")
            .execute()
            .requireStatusCode(200)
            .bindSuccess { it.parseJson<OneJokeJson>() }
            .mapSuccess { it.value }

    fun getRandomJokeAsync(): AsyncHttpResult<JokeJson> =
        Async { getRandomJoke() }

    fun getJokesForCategory(categoryName: String): HttpResult<List<JokeJson>> =
        requestBuilder("$apiUrl/jokes/random/3?limitTo=[${categoryName.toLowerCase(Locale.ROOT)}]")
            .execute()
            .requireStatusCode(200)
            .bindSuccess { it.parseJson<ManyJokesJson>() }
            .mapSuccess { it.value }

    fun getJokesForCategoryAsync(categoryName: String): AsyncHttpResult<List<JokeJson>> =
        Async { getJokesForCategory(categoryName) }
}

typealias OneJokeJson = ApiJsonResponse<JokeJson>
typealias ManyJokesJson = ApiJsonResponse<List<JokeJson>>

data class ApiJsonResponse<T>(val value: T)
data class JokeJson(val id: Int, val joke: String)
