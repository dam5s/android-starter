package io.damo.androidstarter.prelude

sealed class Result<A, E> {

    fun <B> mapSuccess(mapping: (A) -> B): Result<B, E> =
        when (this) {
            is Success -> Success(mapping(value))
            is Failure -> Failure(error)
        }

    fun <B> bindSuccess(mapping: (A) -> Result<B, E>): Result<B, E> =
        when (this) {
            is Success -> mapping(value)
            is Failure -> Failure(error)
        }

    fun <F> mapFailure(mapping: (E) -> F): Result<A, F> =
        when (this) {
            is Success -> Success(value)
            is Failure -> Failure(mapping(error))
        }

    fun <F> bindFailure(mapping: (E) -> Result<A, F>): Result<A, F> =
        when (this) {
            is Success -> Success(value)
            is Failure -> mapping(error)
        }

    fun orElse(other: A): A =
        when (this) {
            is Success -> value
            is Failure -> other
        }

    fun orElse(function: (E) -> A): A =
        when (this) {
            is Success -> value
            is Failure -> function(error)
        }

    fun orNull(): A? =
        when (this) {
            is Success -> value
            is Failure -> null
        }
}

data class Success<A, E>(val value: A) : Result<A, E>()
data class Failure<A, E>(val error: E) : Result<A, E>()
