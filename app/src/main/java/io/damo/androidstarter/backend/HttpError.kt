package io.damo.androidstarter.backend

import io.damo.androidstarter.prelude.AsyncResult
import io.damo.androidstarter.prelude.Failure
import io.damo.androidstarter.prelude.Result
import io.damo.androidstarter.prelude.Success
import java.io.IOException

sealed class HttpError {

    data class Connection(
        val exception: IOException
    ) : HttpError()

    data class UnexpectedStatus(
        val actual: Int,
        val expected: Int
    ) : HttpError()

    data class Deserialization(
        val exception: IOException? = null
    ) : HttpError()

    fun message() =
        when (this) {
            is Connection -> "There was a connection error"
            is UnexpectedStatus -> "There was an error on the server"
            is Deserialization -> "There was an error on the server"
        }
}

typealias HttpResult<T> = Result<T, HttpError>
typealias HttpSuccess<T> = Success<T, HttpError>
typealias HttpFailure<T> = Failure<T, HttpError>
typealias AsyncHttpResult<T> = AsyncResult<T, HttpError>
