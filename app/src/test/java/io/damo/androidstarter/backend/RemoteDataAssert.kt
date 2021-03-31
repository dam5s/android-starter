package io.damo.androidstarter.backend

import io.damo.androidstarter.backend.RemoteData.Error
import io.damo.androidstarter.backend.RemoteData.Loaded
import io.damo.androidstarter.backend.RemoteData.Loading
import io.damo.androidstarter.backend.RemoteData.NotLoaded
import io.damo.androidstarter.testsupport.hasType
import org.assertj.core.api.Assertions

class RemoteDataAssert<T>(private val data: RemoteData<T>) {

    companion object {
        fun <T> assertThat(data: RemoteData<T>) =
            RemoteDataAssert(data)
    }

    fun isNotLoaded() = apply {
        Assertions.assertThat(data).hasType<NotLoaded<T>>()
    }

    fun isLoading() = apply {
        Assertions.assertThat(data).hasType<Loading<T>>()
    }

    fun isLoaded() = apply {
        Assertions.assertThat(data).hasType<Loaded<T>>()
    }

    fun isLoadedWith(expected: T) = apply {
        Assertions.assertThat(data).isEqualTo(Loaded(expected))
    }

    fun hasErrored() = apply {
        Assertions.assertThat(data).hasType<Error<T>>()
    }

    fun hasErroredWith(error: HttpError) = apply {
        Assertions.assertThat(data).isEqualTo(Error<T>(error))
    }
}
