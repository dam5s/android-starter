package io.damo.androidstarter.categories

import io.damo.androidstarter.MainScreen
import io.damo.androidstarter.instrumentationsupport.TestAppContext
import io.damo.androidstarter.instrumentationsupport.startMainActivity

class CategoriesTabTest(testAppContext: TestAppContext) {

    private val mainScreen = MainScreen(testAppContext)

    fun testNavigation() {
        startMainActivity()
        mainScreen.clickOnCategoriesTab()

        mainScreen.checkAllCategoriesAreDisplayed()

        mainScreen.clickCategoryByName("Nerdy")
        mainScreen.checkCategoryIsDisplayed("Nerdy")
        mainScreen.checkNerdyCategoryJokesAreDisplayed()

        mainScreen.clickUpButton()

        mainScreen.checkAllCategoriesAreDisplayed()
    }
}
