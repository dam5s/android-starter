package io.damo.androidstarter.instrumentationsupport

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class TestDispatcher : Dispatcher() {

    companion object {
        const val randomJoke =
            "MacGyver can build an airplane out of gum and paper clips. Chuck Norris can kill him and take it."
    }

    override fun dispatch(request: RecordedRequest): MockResponse =
        when (request.path) {
            "/jokes/random" -> mockResponse(
                """
                { "type": "success", "value": { "id": 2, "joke": "$randomJoke", "categories": [] } }
                """
            )

            else -> mockResponse("Mock server hsd no response", 500)
        }
}
