package io.damo.androidstarter.ui.categories

import io.damo.androidstarter.AppLifeCycle
import io.damo.androidstarter.AppLifeCycle.Action.FinishLoadingCategory
import io.damo.androidstarter.AppLifeCycle.Action.StartLoadingCategory
import io.damo.androidstarter.Category
import io.damo.androidstarter.backend.JokeApi
import io.damo.androidstarter.prelude.Redux
import io.damo.androidstarter.prelude.mapSuccess

object CategoriesInteractions {

    fun load(store: Redux.Store<AppLifeCycle.State>, api: JokeApi, category: Category) {
        store.dispatch(StartLoadingCategory(category))

        api
            .getJokesForCategoryAsync(category)
            .mapSuccess { list -> list.map { CategoryJokeView(it.joke) } }
            .onComplete {
                store.dispatch(FinishLoadingCategory(category, it))
            }
    }
}
