package io.damo.androidstarter.instrumentationsupport

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class TestDispatcher : Dispatcher() {

    private var nextJokeIndex = 0

    var jokeError = false

    companion object {
        val randomJokes = listOf(
            "MacGyver can build an airplane out of gum and paper clips. Chuck Norris can kill him and take it.",
            "Chuck Norris eats beef jerky and craps gunpowder. Then, he uses that gunpowder to make a bullet, which he uses to kill a cow and make more beef jerky. Some people refer to this as the 'Circle of Life.'",
            "In honor of Chuck Norris, all McDonald's in Texas have an even larger size than the super-size. When ordering, just ask to be Chucksized."
        )
    }

    private val errorResponse = mockResponse(code = 500)

    private fun randomJokeResponse(): MockResponse {
        val jokeText = randomJokes[nextJokeIndex]
        nextJokeIndex = (nextJokeIndex + 1) % randomJokes.size

        return mockResponse(
            """
            { "type": "success", "value": { "id": 2, "joke": "$jokeText", "categories": [] } }
            """
        )
    }

    private fun categoryResponse(): MockResponse =
        mockResponse(
            """
            { "type": "success", "value": [
                { "id": 2, "joke": "${randomJokes[0]}", "categories": [] }, 
                { "id": 3, "joke": "${randomJokes[1]}", "categories": [] }, 
                { "id": 4, "joke": "${randomJokes[2]}", "categories": [] } 
            ]}
            """
        )

    override fun dispatch(request: RecordedRequest): MockResponse {
        if (jokeError) return errorResponse

        return when (request.path) {
            "/jokes/random" -> randomJokeResponse()
            "/jokes/random/3?limitTo=[nerdy]" -> categoryResponse()
            else -> mockResponse("Mock server has no response for this request path ${request.path}", 500)
        }
    }
}
