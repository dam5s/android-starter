package io.damo.androidstarter.instrumentationsupport

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

fun startMockServer(d: Dispatcher) =
    MockWebServer().apply {
        dispatcher = d
        start()
    }

fun MockWebServer.baseUrl() =
    url("").toString().removeSuffix("/")

fun mockResponse(body: String = "", code: Int = 200) =
    MockResponse()
        .apply {
            setBody(body)
            setResponseCode(code)
        }
