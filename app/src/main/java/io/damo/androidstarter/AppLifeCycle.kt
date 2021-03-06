package io.damo.androidstarter

import io.damo.androidstarter.AppLifeCycle.Action
import io.damo.androidstarter.AppLifeCycle.Action.FinishLoadingCategory
import io.damo.androidstarter.AppLifeCycle.Action.FinishLoadingRandomJoke
import io.damo.androidstarter.AppLifeCycle.Action.StartLoadingCategory
import io.damo.androidstarter.AppLifeCycle.Action.StartLoadingRandomJoke
import io.damo.androidstarter.AppLifeCycle.State
import io.damo.androidstarter.backend.HttpResult
import io.damo.androidstarter.backend.RemoteData
import io.damo.androidstarter.backend.toRemoteData
import io.damo.androidstarter.joke.JokeView
import io.damo.androidstarter.prelude.Redux

typealias Category = String

object AppLifeCycle {

    data class State(
        val jokesByCategory: Map<Category, RemoteData<List<JokeView>>> = emptyMap(),
        val randomJoke: RemoteData<JokeView> = RemoteData.NotLoaded()
    )

    fun State.categoryJokes(category: Category): RemoteData<List<JokeView>> =
        jokesByCategory[category] ?: RemoteData.NotLoaded()

    sealed class Action : Redux.Action {
        object StartLoadingRandomJoke : Action()
        data class FinishLoadingRandomJoke(val result: HttpResult<JokeView>) : Action()

        data class StartLoadingCategory(val category: Category) : Action()
        data class FinishLoadingCategory(
            val category: Category,
            val result: HttpResult<List<JokeView>>
        ) : Action()
    }

    fun reducer(state: State, action: Redux.Action): State =
        if (action is Action) {
            appReducer(state, action)
        } else {
            state
        }
}

private fun appReducer(state: State, action: Action): State =
    when (action) {
        StartLoadingRandomJoke -> state.copy(randomJoke = RemoteData.Loading())
        is FinishLoadingRandomJoke -> state.copy(randomJoke = action.result.toRemoteData())

        is StartLoadingCategory -> state.startLoading(action.category)
        is FinishLoadingCategory -> state.finishLoading(action.category, action.result)
    }

private fun State.startLoading(category: Category): State =
    jokesByCategory
        .plus(category to RemoteData.Loading())
        .let { updatedJokes -> copy(jokesByCategory = updatedJokes) }

private fun State.finishLoading(category: Category, result: HttpResult<List<JokeView>>): State =
    jokesByCategory
        .plus(category to result.toRemoteData())
        .let { updatedJokes -> copy(jokesByCategory = updatedJokes) }
