package io.damo.androidstarter.randomjoke

import io.damo.androidstarter.AppLifeCycle
import io.damo.androidstarter.AppLifeCycle.Action.FinishLoadingRandomJoke
import io.damo.androidstarter.AppLifeCycle.Action.StartLoadingRandomJoke
import io.damo.androidstarter.backend.JokeApi
import io.damo.androidstarter.ui.JokeView
import io.damo.androidstarter.prelude.Redux
import io.damo.androidstarter.prelude.mapSuccess


fun loadJoke(store: Redux.Store<AppLifeCycle.State>, api: JokeApi) {
    store.dispatch(StartLoadingRandomJoke)

    api
        .getRandomJokeAsync()
        .mapSuccess { JokeView(it.joke) }
        .onComplete { store.dispatch(FinishLoadingRandomJoke(it)) }
}
