package io.damo.androidstarter.prelude

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

interface Action

interface Subscriber<S> {
    fun onStateChanged(state: S)
}

class Store<S>(
    state: S,
    val reducer: (S, Action) -> S
) {

    var state: S = state; private set

    private val subscriptions = mutableListOf<Subscription<S, *>>()
    private val executor = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    private val scope = CoroutineScope(executor)

    fun dispatch(action: Action) {
        scope.launch {
            state = reducer(state, action)
            subscriptions.forEach { it.notifyIfStateChanged(state) }
        }
    }

    fun subscribe(subscriber: Subscriber<S>) {
        scope.launch {
            addSubscription(state, subscriber, { it })
        }
    }

    fun <T> subscribe(subscriber: Subscriber<T>, transform: (S) -> T) {
        scope.launch {
            addSubscription(state, subscriber, transform)
        }
    }

    fun <T> unsubscribe(subscriber: Subscriber<T>) {
        scope.launch {
            val subscription = subscriptions.find { it.subscriber == subscriber }
            subscriptions.remove(subscription)
        }
    }

    private suspend fun <T> addSubscription(
        initialState: S,
        subscriber: Subscriber<T>,
        transform: (S) -> T
    ) {
        val sub = Subscription(initialState, subscriber, transform)
        sub.notify()
        subscriptions.add(sub)
    }
}

private class Subscription<S, T>(
    initialState: S,
    val subscriber: Subscriber<T>,
    val transform: (S) -> T
) {

    private var previousState: T = transform(initialState)

    suspend fun notifyIfStateChanged(state: S) {
        val transformedState = transform(state)
        if (previousState != transformedState) {
            previousState = transformedState
            notify()
        }
    }

    suspend fun notify() {
        withContext(Dispatchers.Main) {
            subscriber.onStateChanged(previousState)
        }
    }
}
