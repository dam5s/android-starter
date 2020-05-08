package io.damo.androidstarter.testsupport

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

fun startMockServer() =
    MockWebServer().apply { start() }

fun MockWebServer.baseUrl() =
    url("").toString().removeSuffix("/")

fun MockWebServer.enqueue(body: String = "", code: Int = 200) =
    MockResponse()
        .apply {
            setBody(body)
            setResponseCode(code)
        }
        .also { enqueue(it) }
