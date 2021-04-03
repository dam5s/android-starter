package io.damo.androidstarter

import io.damo.androidstarter.AppLifeCycle.Action
import io.damo.androidstarter.AppLifeCycle.Action.FinishLoadingCategory
import io.damo.androidstarter.AppLifeCycle.Action.FinishLoadingRandomJoke
import io.damo.androidstarter.AppLifeCycle.Action.StartLoadingCategory
import io.damo.androidstarter.AppLifeCycle.Action.StartLoadingRandomJoke
import io.damo.androidstarter.AppLifeCycle.Action.SelectTab
import io.damo.androidstarter.AppLifeCycle.State
import io.damo.androidstarter.backend.HttpResult
import io.damo.androidstarter.backend.RemoteData
import io.damo.androidstarter.backend.toRemoteData
import io.damo.androidstarter.ui.randomjoke.RandomJokeView
import io.damo.androidstarter.prelude.Redux
import io.damo.androidstarter.ui.categories.CategoryJokeView

typealias Category = String

typealias Dispatch = (AppLifeCycle.Action) -> Unit

object AppLifeCycle {
    enum class Tab {
        Random,
        Categories
    }

    data class State(
        val tab: Tab = Tab.Random,
        val jokesByCategory: Map<Category, RemoteData<List<CategoryJokeView>>> = emptyMap(),
        val randomJoke: RemoteData<RandomJokeView> = RemoteData.NotLoaded()
    )

    fun State.categoryJokes(category: Category): RemoteData<List<CategoryJokeView>> =
        jokesByCategory[category] ?: RemoteData.NotLoaded()

    sealed class Action : Redux.Action {
        data class SelectTab(val tab: Tab) : Action()

        object StartLoadingRandomJoke : Action()
        data class FinishLoadingRandomJoke(val result: HttpResult<RandomJokeView>) : Action()

        data class StartLoadingCategory(val category: Category) : Action()
        data class FinishLoadingCategory(
            val category: Category,
            val result: HttpResult<List<CategoryJokeView>>
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
        is SelectTab -> state.copy(tab = action.tab)

        StartLoadingRandomJoke -> state.copy(randomJoke = state.randomJoke.startLoading())
        is FinishLoadingRandomJoke -> state.copy(randomJoke = action.result.toRemoteData())

        is StartLoadingCategory -> state.startLoading(action.category)
        is FinishLoadingCategory -> state.finishLoading(action.category, action.result)
    }

private fun State.startLoading(category: Category): State =
    jokesByCategory
        .plus(category to RemoteData.Loading())
        .let { updatedJokes -> copy(jokesByCategory = updatedJokes) }

private fun State.finishLoading(category: Category, result: HttpResult<List<CategoryJokeView>>): State =
    jokesByCategory
        .plus(category to result.toRemoteData())
        .let { updatedJokes -> copy(jokesByCategory = updatedJokes) }
