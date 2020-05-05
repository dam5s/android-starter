package io.damo.androidstarter

import io.damo.androidstarter.support.*

class JokeApi(private val apiUrl: String) {

    fun getRandomJoke(): Result<JokeJson> =
        requestBuilder("$apiUrl/jokes/random")
            .execute()
            .bind { it.parseJson<RandomJokeJson>() }
            .map { it.value }

    data class RandomJokeJson(
        val type: String,
        val value: JokeJson
    )

    data class JokeJson(
        val id: Int,
        val joke: String
    )
}
