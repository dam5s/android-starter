package io.damo.androidstarter.prelude

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

fun <S, T> Redux.Store<S>.stateOf(transform: (S) -> T): State<T> {
    val mutableState = mutableStateOf(transform(state))

    val subscriber = object : Redux.Subscriber<T> {
        override fun onStateChanged(state: T) {
            mutableState.value = state
        }
    }

    subscribe(subscriber, transform)

    return mutableState
}
