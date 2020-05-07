package io.damo.androidstarter.support

fun <T> LiveRemoteData<T>.isNotLoaded() =
    value is RemoteData.NotLoaded

fun <T> LiveRemoteData<T>.isLoading() =
    value is RemoteData.Loading

fun <T> LiveRemoteData<T>.isLoaded() =
    value is RemoteData.Loaded

fun <T> LiveRemoteData<T>.isLoadedWith(expected: T) =
    value == RemoteData.Loaded(expected)

fun <T> LiveRemoteData<T>.hasErrored() =
    value is RemoteData.Error

fun <T> LiveRemoteData<T>.hasErroredWith(expected: Explanation) =
    value == RemoteData.Error<T>(expected)
