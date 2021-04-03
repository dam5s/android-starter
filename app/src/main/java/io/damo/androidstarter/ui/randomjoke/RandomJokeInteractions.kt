package io.damo.androidstarter.ui.randomjoke

import io.damo.androidstarter.AppLifeCycle.Action.FinishLoadingRandomJoke
import io.damo.androidstarter.AppLifeCycle.Action.StartLoadingRandomJoke
import io.damo.androidstarter.Dispatch
import io.damo.androidstarter.backend.JokeApi
import io.damo.androidstarter.prelude.mapSuccess


object RandomJokeInteractions {
    fun load(dispatch: Dispatch, api: JokeApi) {
        dispatch(StartLoadingRandomJoke)

        api
            .getRandomJokeAsync()
            .mapSuccess { RandomJokeView(it.joke) }
            .onComplete { dispatch(FinishLoadingRandomJoke(it)) }
    }
}
