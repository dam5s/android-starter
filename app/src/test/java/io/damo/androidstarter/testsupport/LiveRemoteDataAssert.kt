package io.damo.androidstarter.testsupport

import io.damo.androidstarter.support.Explanation
import io.damo.androidstarter.support.LiveRemoteData
import io.damo.androidstarter.support.RemoteData.Error
import io.damo.androidstarter.support.RemoteData.Loaded
import io.damo.androidstarter.support.RemoteData.Loading
import io.damo.androidstarter.support.RemoteData.NotLoaded
import org.assertj.core.api.Assertions.assertThat

class LiveRemoteDataAssert<T>(data: LiveRemoteData<T>) {

    private val value = data.value

    companion object {
        fun <T> assertThat(data: LiveRemoteData<T>) =
            LiveRemoteDataAssert(data)
    }

    fun isNotLoaded() = apply {
        assertThat(value).hasType<NotLoaded<T>>()
    }

    fun isLoading() = apply {
        assertThat(value).hasType<Loading<T>>()
    }

    fun isLoaded() = apply {
        assertThat(value).hasType<Loaded<T>>()
    }

    fun isLoadedWith(expected: T) = apply {
        assertThat(value).isEqualTo(Loaded(expected))
    }

    fun hasErrored() = apply {
        assertThat(value).hasType<Error<T>>()
    }

    fun hasErroredWith(reason: Explanation) = apply {
        assertThat(value).isEqualTo(Error<T>(reason))
    }
}
