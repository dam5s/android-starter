package io.damo.androidstarter

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeDown
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import io.damo.androidstarter.instrumentationsupport.TestAppContext
import io.damo.androidstarter.instrumentationsupport.waitForText

class MainScreen(testAppContext: TestAppContext) {

    fun waitForJoke(jokeText: String) {
        waitForText(jokeText)
        onView(withId(R.id.jokeTextView)).check(matches(withText(jokeText)))
    }

    fun assertServerErrorDisplayed() {
        onView(withId(R.id.jokeTextView)).check(matches(withText("Expected server response to be a 200")))
    }

    fun startRefresh() {
        onView(withId(R.id.swipeRefresh)).perform(swipeDown())
    }

    fun clickOnCategoriesTab() {
        onView(withId(R.id.categoriesNavigationItem)).perform(click())
    }

    fun checkCategoriesTabIsDisplayed() {
        waitForText("Categories screen")
    }

    fun clickOnRandomTab() {
        onView(withId(R.id.randomNavigationItem)).perform(click())
    }

    fun checkRandomTabIsDisplayed() {
        onView(withId(R.id.jokeTextView)).check(matches(isDisplayed()))
    }
}
