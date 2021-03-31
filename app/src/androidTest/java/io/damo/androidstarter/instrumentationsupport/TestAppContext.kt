package io.damo.androidstarter.instrumentationsupport

import io.damo.androidstarter.AppLifeCycle
import io.damo.androidstarter.StarterApp
import io.damo.androidstarter.prelude.Redux

class TestAppContext(app: StarterApp) {

    val testComponent = TestAppComponent(app)

    init {
        app.appComponent = testComponent
        app.stateStore = Redux.Store(testComponent.initialState, AppLifeCycle::reducer)
    }

    fun tearDown() {
        device().setOrientationNatural()
        testComponent.shutdownServer()
    }
}
