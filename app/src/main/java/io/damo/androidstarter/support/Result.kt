package io.damo.androidstarter.support

sealed class Result<A> {

    fun <B> map(mapping: (A) -> B): Result<B> =
        when (this) {
            is Success -> Success(mapping(value))
            is Failure -> Failure(reason)
        }
    fun <B> bind(mapping: (A) -> Result<B>): Result<B> =
        when (this) {
            is Success -> mapping(value)
            is Failure -> Failure(reason)
        }
    fun orElse(other: A): A =
        when (this) {
            is Success -> value
            is Failure -> other
        }
    fun orElse(function: (Explanation) -> A): A =
        when (this) {
            is Success -> value
            is Failure -> function(reason)
        }
    fun orNull(): A? =
        when (this) {
            is Success -> value
            is Failure -> null
        }
}

data class Success<A>(val value: A) : Result<A>()

data class Failure<A>(val reason: Explanation) : Result<A>() {

    constructor(message: String) :
        this(Explanation(message, listOf()))

    constructor(message: String, exception: Exception) :
        this(Explanation(message, listOf(exception)))
}

data class Explanation(val message: String, val exceptions: List<Exception>)
