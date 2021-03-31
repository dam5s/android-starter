package io.damo.androidstarter

import android.graphics.Point
import androidx.test.uiautomator.UiDevice
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

        rotateLeft()

        waitForText(R.string.categories_title)

        rotateNatural()

        waitForText(R.string.categories_title)
    }
}

private fun UiDevice.rotateWith(rotate: (UiDevice) -> Unit) {
    val previousResolution = displaySizeDp

    rotate(this)

    await until {
        displaySizeDp == Point(previousResolution.y, previousResolution.x)
    }
}

private fun rotateLeft() = device().rotateWith { it.setOrientationLeft() }
private fun rotateNatural() = device().rotateWith { it.setOrientationNatural() }
