package io.damo.androidstarter.ui

import io.damo.androidstarter.AppLifeCycle
import io.damo.androidstarter.AppLifeCycle.Action.FinishLoadingCategory
import io.damo.androidstarter.AppLifeCycle.Action.StartLoadingCategory
import io.damo.androidstarter.Category
import io.damo.androidstarter.backend.JokeApi
import io.damo.androidstarter.prelude.Redux
import io.damo.androidstarter.prelude.mapSuccess

object CategoryJokesInteractions {

    fun loadCategory(store: Redux.Store<AppLifeCycle.State>, api: JokeApi, category: Category) {
        store.dispatch(StartLoadingCategory(category))

        api
            .getJokesForCategoryAsync(category)
            .mapSuccess { list -> list.map { JokeView(it.joke) } }
            .onComplete {
                store.dispatch(FinishLoadingCategory(category, it))
            }
    }
}
