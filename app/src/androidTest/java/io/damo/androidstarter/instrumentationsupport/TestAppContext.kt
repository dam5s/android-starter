package io.damo.androidstarter.instrumentationsupport

import io.damo.androidstarter.AppLifeCycle
import io.damo.androidstarter.StarterApp
import io.damo.androidstarter.prelude.Store

class TestAppContext(app: StarterApp) {

    val testComponent = TestAppComponent(app)

    init {
        app.appComponent = testComponent
        app.stateStore = Store(testComponent.initialState, AppLifeCycle::reducer)
    }

    fun tearDown() {
        device().setOrientationNatural()
        testComponent.shutdownServer()
    }
}
