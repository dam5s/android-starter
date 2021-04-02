package io.damo.androidstarter

import android.app.Application
import android.content.Context
import io.damo.androidstarter.prelude.Redux

class StarterApp : Application() {

    lateinit var appComponent: AppComponent
    lateinit var stateStore: Redux.Store<AppLifeCycle.State>

    override fun onCreate() {
        super.onCreate()
        appComponent = DefaultAppComponent()
        stateStore = Redux.Store(appComponent.initialState, AppLifeCycle::reducer)
    }
}

val Context.appComponent: AppComponent
    get() = (applicationContext as StarterApp).appComponent

val Context.stateStore: Redux.Store<AppLifeCycle.State>
    get() = (applicationContext as StarterApp).stateStore
