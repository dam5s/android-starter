package io.damo.androidstarter

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import io.damo.androidstarter.instrumentationsupport.TestAppComponent
import io.damo.androidstarter.instrumentationsupport.TestDispatcher.Companion.randomJoke
import junit.framework.TestCase.fail
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FlowTest {

    @get:Rule
    val activityRule: ActivityTestRule<FlowTestStartActivity> =
        ActivityTestRule(FlowTestStartActivity::class.java)

    @Test
    fun test() {
        val app = activityRule.activity.application as StarterApp
        app.appComponent = TestAppComponent(app)

        onView(withId(R.id.startMainActivity)).perform(click())

        waitForText(randomJoke)
    }

    private fun device(): UiDevice =
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    private fun waitForText(text: String, timeoutInMs: Long = 500) {
        device()
            .wait(Until.findObject(By.textContains(text)), timeoutInMs)
            ?: fail("Timed out waiting for text: $text")
    }
}
