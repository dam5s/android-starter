package io.damo.androidstarter.randomjoke

import io.damo.androidstarter.MainScreen
import io.damo.androidstarter.instrumentationsupport.TestAppContext
import io.damo.androidstarter.instrumentationsupport.TestDispatcher.Companion.randomJokes
import io.damo.androidstarter.instrumentationsupport.startMainActivity

class RandomJokeTabTest(testAppContext: TestAppContext) {

    private val mainScreen = MainScreen(testAppContext)
    private val testAppComponent = testAppContext.testComponent
    private val testDispatcher = testAppComponent.testDispatcher

    fun testCreation_OnLoadFailure() {
        testDispatcher.jokeError = true

        startMainActivity()

        mainScreen.assertServerErrorDisplayed()
    }

    fun testCreation_RefreshingTheJoke() {
        startMainActivity()

        mainScreen.waitForJoke(randomJokes[0])

        mainScreen.startRefresh()

        mainScreen.waitForJoke(randomJokes[1])

        mainScreen.startRefresh()

        mainScreen.waitForJoke(randomJokes[2])
    }
}
