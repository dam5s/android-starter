package io.damo.androidstarter

import io.damo.androidstarter.instrumentationsupport.TestAppContext
import io.damo.androidstarter.instrumentationsupport.TestDispatcher.Companion.randomJokes
import io.damo.androidstarter.instrumentationsupport.startMainActivity

class MainActivityTest(testAppContext: TestAppContext) {

    private val mainScreen = MainScreen(testAppContext)

    fun testNavigation() {
        startMainActivity()

        mainScreen.waitForJoke(randomJokes.first())

        mainScreen.clickOnCategoriesTab()
        mainScreen.checkCategoriesTabIsDisplayed()

        mainScreen.clickOnRandomTab()
        mainScreen.checkRandomTabIsDisplayed()
    }
}
