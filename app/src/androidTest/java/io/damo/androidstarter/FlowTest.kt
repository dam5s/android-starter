package io.damo.androidstarter

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import io.damo.androidstarter.instrumentationsupport.TestAppComponent
import io.damo.androidstarter.instrumentationsupport.TestDispatcher.Companion.randomJoke
import io.damo.androidstarter.instrumentationsupport.waitForButtonEnabled
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FlowTest {

    @get:Rule
    val activityRule: ActivityTestRule<FlowTestStartActivity> =
        ActivityTestRule(FlowTestStartActivity::class.java)

    private val app: StarterApp
        get() = activityRule.activity.application as StarterApp

    @Test
    fun test() {
        app.appComponent = TestAppComponent(app)
        val refreshButtonText = app
            .getString(R.string.refresh)
            .toUpperCase()

        onView(withId(R.id.startMainActivity)).perform(click())

        waitForButtonEnabled(refreshButtonText)
        onView(withId(R.id.jokeTextView)).check(matches(withText(randomJoke)))
    }
}
