package io.damo.androidstarter.support

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import io.damo.androidstarter.support.RemoteData.Error
import io.damo.androidstarter.support.RemoteData.Loaded

sealed class RemoteData<T> {
    class NotLoaded<T>: RemoteData<T>()
    class Loading<T>: RemoteData<T>()
    data class Loaded<T>(val data: T): RemoteData<T>()
    data class Error<T>(val explanation: Explanation): RemoteData<T>()
}

fun <T> Result<T>.toRemoteData(): RemoteData<T> =
    when (this) {
        is Success -> Loaded(this.value)
        is Failure -> Error(this.reason)
    }

typealias LiveRemoteData<T> = LiveData<RemoteData<T>>
typealias MutableLiveRemoteData<T> = MutableLiveData<RemoteData<T>>

fun <T> LifecycleOwner.observe(observable: LiveData<T>, function: (T) -> Unit) =
    observable.observe(this, Observer { function(it) })

inline fun <reified T> createLiveRemoteData(): MutableLiveRemoteData<T> =
    MutableLiveData<RemoteData<T>>().apply { value = RemoteData.NotLoaded() }
