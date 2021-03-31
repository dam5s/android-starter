package io.damo.androidstarter.prelude

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.Result.Companion.success
import kotlin.coroutines.suspendCoroutine

typealias Callback<T> = suspend (T) -> Unit

class Async<T>(
    val scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    val function: suspend () -> T
) {

    fun onComplete(callback: Callback<T>) {
        scope.launch {
            val value = onDefault { function() }
            onMain { callback(value) }
        }
    }

    fun <U> map(function: (T) -> U): Async<U> {
        val newFunction = suspend {
            suspendCoroutine<U> { continuation ->
                onComplete {
                    val mapped = onDefault { function(it) }

                    continuation.resumeWith(success(mapped))
                }
            }
        }
        return Async(function = newFunction)
    }

    fun <U> bind(function: (T) -> Async<U>): Async<U> {
        val newFunction = suspend {
            suspendCoroutine<U> { continuation ->
                onComplete { initial ->
                    val binding = onDefault { function(initial) }

                    binding.onComplete { mapped ->
                        continuation.resumeWith(success(mapped))
                    }
                }
            }
        }
        return Async(function = newFunction)
    }

    private suspend fun <U> onMain(function: suspend CoroutineScope.() -> U) =
        withContext(Dispatchers.Main, function)

    private suspend fun <U> onDefault(function: suspend CoroutineScope.() -> U) =
        withContext(Dispatchers.Default, function)
}
