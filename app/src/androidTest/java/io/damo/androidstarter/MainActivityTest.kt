package io.damo.androidstarter

import android.graphics.Point
import io.damo.androidstarter.instrumentationsupport.TestAppContext
import io.damo.androidstarter.instrumentationsupport.TestDispatcher.Companion.randomJokes
import io.damo.androidstarter.instrumentationsupport.device
import io.damo.androidstarter.instrumentationsupport.startMainActivity
import io.damo.androidstarter.instrumentationsupport.waitForText
import org.awaitility.kotlin.await
import org.awaitility.kotlin.until

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

    fun testSavingStateOnScreenRotation() {
        startMainActivity()

        mainScreen.clickOnCategoriesTab()
        mainScreen.checkCategoriesTabIsDisplayed()

        rotateScreen()

        waitForText(R.string.categories_title)
    }
}

private fun rotateScreen() {
    val device = device()
    val portraitResolution = device.displaySizeDp

    device.setOrientationLeft()

    await until {
        device.displaySizeDp == Point(portraitResolution.y, portraitResolution.x)
    }
}
