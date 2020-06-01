package io.damo.androidstarter

import io.damo.androidstarter.instrumentationsupport.TestAppContext
import io.damo.androidstarter.instrumentationsupport.TestDispatcher.Companion.randomJokes
import io.damo.androidstarter.instrumentationsupport.startMainActivity

class MainActivityTest(testAppContext: TestAppContext) {

    private val mainScreen = MainScreen(testAppContext)
    private val testAppComponent = testAppContext.testComponent
    private val appPreferences = testAppComponent.appPreferences
    private val testDispatcher = testAppComponent.testDispatcher

    fun testCreation() {
        startMainActivity()

        mainScreen.waitForRefreshButtonEnabled()
        mainScreen.assertJokeDisplayed(randomJokes.first())
    }

    fun testCreation_WhenJokeIsCached() {
        val cachedJoke =
            "Chuck Norris eats beef jerky and craps gunpowder. Then, he uses that gunpowder to make a bullet, which he uses to kill a cow and make more beef jerky. Some people refer to this as the 'Circle of Life.'"
        appPreferences.saveJoke(cachedJoke)

        startMainActivity()

        mainScreen.assertJokeDisplayed(cachedJoke)
    }

    fun testCreation_OnLoadFailure() {
        testDispatcher.jokeError = true

        startMainActivity()

        mainScreen.waitForRefreshButtonEnabled()
        mainScreen.assertServerErrorDisplayed()
    }

    fun testCreation_RefreshingTheJoke() {
        startMainActivity()

        mainScreen.waitForRefreshButtonEnabled()
        mainScreen.assertJokeDisplayed(randomJokes[0])

        mainScreen.clickRefresh()

        mainScreen.waitForRefreshButtonEnabled()
        mainScreen.assertJokeDisplayed(randomJokes[1])

        mainScreen.clickRefresh()

        mainScreen.waitForRefreshButtonEnabled()
        mainScreen.assertJokeDisplayed(randomJokes[2])
    }
}
