package io.damo.androidstarter.prelude

typealias AsyncResult<T, E> = Async<Result<T, E>>

fun <T, U, E> AsyncResult<T, E>.mapSuccess(function: (T) -> U): AsyncResult<U, E> =
    map { result -> result.mapSuccess(function) }

fun <T, E, F> AsyncResult<T, E>.mapFailure(function: (E) -> F): AsyncResult<T, F> =
    map { result -> result.mapFailure(function) }
