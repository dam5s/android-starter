package io.damo.androidstarter.backend

import io.damo.androidstarter.prelude.Failure
import io.damo.androidstarter.prelude.Success

sealed class RemoteData<T> {
    class NotLoaded<T> : RemoteData<T>()
    class Loading<T> : RemoteData<T>()
    data class Loaded<T>(val data: T) : RemoteData<T>()
    data class Error<T>(val error: HttpError) : RemoteData<T>()
}

fun <T> HttpResult<T>.toRemoteData(): RemoteData<T> =
    when (this) {
        is Success -> RemoteData.Loaded(value)
        is Failure -> RemoteData.Error(error)
    }
