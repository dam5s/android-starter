package io.damo.androidstarter.instrumentationsupport

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import junit.framework.TestCase.fail

fun device(): UiDevice =
    UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

fun waitForButtonEnabled(text: String, isEnabled: Boolean = true, timeoutInMs: Long = 500) {
    waitForObject(
        By.textContains(text).enabled(isEnabled),
        timeoutInMs
    )
}

fun waitForText(text: String, timeoutInMs: Long = 500) =
    waitForObject(By.textContains(text), timeoutInMs)

fun waitForObject(bySelector: BySelector, timeoutInMs: Long) {
    device()
        .wait(Until.findObject(bySelector), timeoutInMs)
        ?: fail("Timed out waiting for object by selector: $bySelector")
}
