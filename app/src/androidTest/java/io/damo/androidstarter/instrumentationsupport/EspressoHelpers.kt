package io.damo.androidstarter.instrumentationsupport

import androidx.annotation.StringRes
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import io.damo.androidstarter.R
import io.damo.androidstarter.StarterApp
import junit.framework.TestCase.fail

fun startMainActivity() {
    onView(withId(R.id.startMainActivity)).perform(click())
}

fun device(): UiDevice =
    UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

fun waitForButtonEnabled(text: String, isEnabled: Boolean = true, timeoutInMs: Long = 500) {
    waitForObject(
        By.textContains(text).enabled(isEnabled),
        timeoutInMs
    )
}

fun stringFromResId(@StringRes stringRes: Int): String =
    ApplicationProvider
        .getApplicationContext<StarterApp>()
        .getString(stringRes)

fun waitForText(@StringRes stringRes: Int, timeoutInMs: Long = 500) =
    waitForText(stringFromResId(stringRes), timeoutInMs)

fun waitForText(text: String, timeoutInMs: Long = 500) =
    waitForObject(By.textContains(text), timeoutInMs)

fun waitForObject(bySelector: BySelector, timeoutInMs: Long) {
    device()
        .wait(Until.findObject(bySelector), timeoutInMs)
        ?: fail("Timed out waiting for object by selector: $bySelector")
}
