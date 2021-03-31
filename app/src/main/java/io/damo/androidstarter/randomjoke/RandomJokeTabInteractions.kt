package io.damo.androidstarter.randomjoke

import io.damo.androidstarter.AppLifeCycle
import io.damo.androidstarter.AppLifeCycle.AppAction.FinishLoadingRandomJoke
import io.damo.androidstarter.AppLifeCycle.AppAction.StartLoadingRandomJoke
import io.damo.androidstarter.backend.JokeApi
import io.damo.androidstarter.joke.JokeView
import io.damo.androidstarter.prelude.Store
import io.damo.androidstarter.prelude.mapSuccess


fun loadJoke(store: Store<AppLifeCycle.State>, api: JokeApi) {
    store.dispatch(StartLoadingRandomJoke)

    api
        .getRandomJokeAsync()
        .mapSuccess { JokeView(it.joke) }
        .onComplete { store.dispatch(FinishLoadingRandomJoke(it)) }
}
