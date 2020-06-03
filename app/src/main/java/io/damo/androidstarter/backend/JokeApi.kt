package io.damo.androidstarter.backend

import io.damo.androidstarter.support.Result
import java.util.Locale

class JokeApi(private val apiUrl: String) {

    fun getRandomJoke(): Result<JokeJson> =
        requestBuilder("$apiUrl/jokes/random")
            .execute()
            .requireStatusCode(200)
            .bind { it.parseJson<OneJokeJson>() }
            .map { it.value }

    fun getJokesForCategory(categoryName: String): Result<List<JokeJson>> =
        requestBuilder("$apiUrl/jokes/random/3?limitTo=[${categoryName.toLowerCase(Locale.ROOT)}]")
            .execute()
            .requireStatusCode(200)
            .bind { it.parseJson<ManyJokesJson>() }
            .map { it.value }
}

typealias OneJokeJson = ApiJsonResponse<JokeJson>
typealias ManyJokesJson = ApiJsonResponse<List<JokeJson>>

data class ApiJsonResponse<T>(val value: T)
data class JokeJson(val id: Int, val joke: String)
