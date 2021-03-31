package io.damo.androidstarter.categories

import io.damo.androidstarter.AppLifeCycle
import io.damo.androidstarter.Category
import io.damo.androidstarter.backend.JokeApi
import io.damo.androidstarter.joke.JokeView
import io.damo.androidstarter.prelude.Store
import io.damo.androidstarter.prelude.mapSuccess

object CategoryJokesInteractions {

    fun loadCategory(store: Store<AppLifeCycle.State>, api: JokeApi, category: Category) {
        store.dispatch(AppLifeCycle.AppAction.StartLoadingCategory(category))

        api
            .getJokesForCategoryAsync(category)
            .mapSuccess { list -> list.map { JokeView(it.joke) } }
            .onComplete {
                store.dispatch(AppLifeCycle.AppAction.FinishLoadingCategory(category, it))
            }
    }
}
