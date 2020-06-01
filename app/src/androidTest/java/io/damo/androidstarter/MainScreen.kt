package io.damo.androidstarter

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import io.damo.androidstarter.instrumentationsupport.TestAppContext
import io.damo.androidstarter.instrumentationsupport.waitForButtonEnabled

class MainScreen(testAppContext: TestAppContext) {

    private val app = testAppContext.app

    fun assertJokeDisplayed(jokeText: String) {
        onView(withId(R.id.jokeTextView)).check(matches(withText(jokeText)))
    }

    fun waitForRefreshButtonEnabled() {
        val refreshButtonText = app
            .getString(R.string.refresh)
            .toUpperCase()

        waitForButtonEnabled(refreshButtonText)
    }

    fun assertServerErrorDisplayed() {
        onView(withId(R.id.jokeTextView)).check(matches(withText("Expected server response to be a 200")))
    }

    fun clickRefresh() {
        onView(withId(R.id.refreshButton)).perform(click())
    }
}
