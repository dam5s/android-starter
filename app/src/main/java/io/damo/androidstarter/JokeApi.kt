package io.damo.androidstarter

import io.damo.androidstarter.support.Result
import io.damo.androidstarter.support.execute
import io.damo.androidstarter.support.parseJson
import io.damo.androidstarter.support.requestBuilder
import io.damo.androidstarter.support.requireStatusCode

class JokeApi(private val apiUrl: String) {

    fun getRandomJoke(): Result<JokeJson> =
        requestBuilder("$apiUrl/jokes/random")
            .execute()
            .requireStatusCode(200)
            .bind { it.parseJson<RandomJokeJson>() }
            .map { it.value }

    data class RandomJokeJson(
        val value: JokeJson
    )

    data class JokeJson(
        val id: Int,
        val joke: String
    )
}
