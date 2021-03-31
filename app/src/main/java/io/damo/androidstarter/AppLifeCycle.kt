package io.damo.androidstarter

import io.damo.androidstarter.AppLifeCycle.AppAction.FinishLoadingCategory
import io.damo.androidstarter.AppLifeCycle.AppAction.FinishLoadingRandomJoke
import io.damo.androidstarter.AppLifeCycle.AppAction.StartLoadingCategory
import io.damo.androidstarter.AppLifeCycle.AppAction.StartLoadingRandomJoke
import io.damo.androidstarter.backend.HttpResult
import io.damo.androidstarter.backend.RemoteData
import io.damo.androidstarter.backend.toRemoteData
import io.damo.androidstarter.joke.JokeView
import io.damo.androidstarter.prelude.Action

typealias Category = String

object AppLifeCycle {

    data class State(
        val jokesByCategory: Map<Category, RemoteData<List<JokeView>>> = emptyMap(),
        val randomJoke: RemoteData<JokeView> = RemoteData.NotLoaded()
    )

    fun State.categoryJokes(category: Category): RemoteData<List<JokeView>> =
        jokesByCategory[category] ?: RemoteData.NotLoaded()

    sealed class AppAction : Action {
        object StartLoadingRandomJoke : AppAction()
        data class FinishLoadingRandomJoke(val result: HttpResult<JokeView>) : AppAction()

        data class StartLoadingCategory(val category: Category) : AppAction()
        data class FinishLoadingCategory(
            val category: Category,
            val result: HttpResult<List<JokeView>>
        ) : AppAction()
    }

    fun reducer(state: State, action: Action): State =
        if (action is AppAction) {
            appReducer(state, action)
        } else {
            state
        }
}

private fun appReducer(
    state: AppLifeCycle.State,
    action: AppLifeCycle.AppAction
): AppLifeCycle.State =
    when (action) {
        StartLoadingRandomJoke -> state.copy(randomJoke = RemoteData.Loading())
        is FinishLoadingRandomJoke -> state.copy(randomJoke = action.result.toRemoteData())

        is StartLoadingCategory -> state.startLoading(action.category)
        is FinishLoadingCategory -> state.finishLoading(action.category, action.result)
    }

private fun AppLifeCycle.State.startLoading(category: Category): AppLifeCycle.State =
    jokesByCategory
        .plus(category to RemoteData.Loading())
        .let { updatedJokes -> copy(jokesByCategory = updatedJokes) }

private fun AppLifeCycle.State.finishLoading(
    category: Category,
    result: HttpResult<List<JokeView>>
): AppLifeCycle.State =
    jokesByCategory
        .plus(category to result.toRemoteData())
        .let { updatedJokes -> copy(jokesByCategory = updatedJokes) }
