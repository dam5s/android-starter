package io.damo.androidstarter

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import io.damo.androidstarter.prelude.Redux

class StarterApp : Application() {

    lateinit var appComponent: AppComponent
    lateinit var stateStore: Redux.Store<AppLifeCycle.State>

    override fun onCreate() {
        super.onCreate()
        appComponent = DefaultAppComponent(this)
        stateStore = Redux.Store(appComponent.initialState, AppLifeCycle::reducer)
    }
}

val Context.appComponent: AppComponent
    get() = (applicationContext as StarterApp).appComponent

val Context.stateStore: Redux.Store<AppLifeCycle.State>
    get() = (applicationContext as StarterApp).stateStore

val Fragment.appComponent: AppComponent
    get() = requireContext().appComponent

val Fragment.stateStore: Redux.Store<AppLifeCycle.State>
    get() = requireContext().stateStore
