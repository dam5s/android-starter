package io.damo.androidstarter.backend

import io.damo.androidstarter.testsupport.baseUrl
import io.damo.androidstarter.testsupport.enqueue
import io.damo.androidstarter.testsupport.startMockServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Test

class JokeApiTest {

    private val mockServer = startMockServer()
    private val api = JokeApi(mockServer.baseUrl())

    @After
    fun teardown() {
        mockServer.shutdown()
    }

    @Test
    fun testGetRandomJoke() {
        val jokeContent =
            "Chuck Norris doesn't need a keyboard he tells the computer to write something and it does."

        mockServer.enqueue(
            body = """{
                "type": "success", 
                "value": { 
                    "id": 605, 
                    "joke": "$jokeContent", 
                    "categories": ["nerdy"] 
                } 
            }"""
        )

        val result = api.getRandomJoke()

        val expectedJoke = JokeJson(605, jokeContent)
        assertThat(result).isEqualTo(HttpSuccess(expectedJoke))

        val recordedRequest = mockServer.takeRequest()
        assertThat(recordedRequest.method).isEqualTo("GET")
        assertThat(recordedRequest.path).isEqualTo("/jokes/random")
    }

    @Test
    fun getJokesForCategory() {
        mockServer.enqueue(
            body = """{
                "type": "success", 
                "value": [
                    { "id": 605, "joke": "Joke #605" }, 
                    { "id": 606, "joke": "Joke #606" }, 
                    { "id": 607, "joke": "Joke #607" } 
                ]
            }"""
        )

        val result = api.getJokesForCategory("My-Category")

        val expectedJokes = listOf(
            JokeJson(605, "Joke #605"),
            JokeJson(606, "Joke #606"),
            JokeJson(607, "Joke #607")
        )
        assertThat(result).isEqualTo(HttpSuccess(expectedJokes))

        val recordedRequest = mockServer.takeRequest()
        assertThat(recordedRequest.method).isEqualTo("GET")
        assertThat(recordedRequest.path).isEqualTo("/jokes/random/3?limitTo=[my-category]")
    }
}
