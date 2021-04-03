package io.damo.androidstarter

import android.app.Application
import android.content.Context
import io.damo.androidstarter.prelude.Redux

class StarterApp : Application() {

    lateinit var appContainer: AppContainer
    lateinit var stateStore: Redux.Store<AppLifeCycle.State>

    override fun onCreate() {
        super.onCreate()
        appContainer = DefaultAppContainer()
        stateStore = Redux.Store(appContainer.initialState, AppLifeCycle::reducer)
    }
}

val Context.appContainer: AppContainer
    get() = (applicationContext as StarterApp).appContainer

val Context.stateStore: Redux.Store<AppLifeCycle.State>
    get() = (applicationContext as StarterApp).stateStore
