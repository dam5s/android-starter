package io.damo.androidstarter

import android.app.Activity
import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import io.damo.androidstarter.instrumentationsupport.TestAppContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConnectedTestRunner {

    @get:Rule
    val activityRule: ActivityTestRule<FlowTestStartActivity> =
        ActivityTestRule(FlowTestStartActivity::class.java)

    lateinit var app: StarterApp
    lateinit var testAppContext: TestAppContext

    @Before
    fun setUp() {
        app = activityRule.activity.applicationContext as StarterApp
        testAppContext = TestAppContext(app)
    }

    private fun reset() {
        testAppContext.tearDown()
        testAppContext = TestAppContext(app)
        startTestActivity<FlowTestStartActivity>()
    }

    @Test
    fun allTests() {
        val allTests = listOf(
            { AppPreferencesTest(testAppContext).testSaveJoke() },
            { AppPreferencesTest(testAppContext).testGetJoke() },

            { MainActivityTest(testAppContext).testCreation() },
            { MainActivityTest(testAppContext).testCreation_WhenJokeIsCached() },
            { MainActivityTest(testAppContext).testCreation_RefreshingTheJoke() },
            { MainActivityTest(testAppContext).testCreation_OnLoadFailure() }
        )

        allTests.forEach { test ->
            reset()
            test()
        }
    }

    inline fun <reified T : Activity> startTestActivity(config: Intent.() -> Unit = {}) {
        val intent = Intent(app, T::class.java)
            .apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) }
            .apply(config)

        InstrumentationRegistry
            .getInstrumentation()
            .startActivitySync(intent)
    }
}