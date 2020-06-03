package io.damo.androidstarter

import android.app.Activity
import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import io.damo.androidstarter.categories.CategoriesTabTest
import io.damo.androidstarter.instrumentationsupport.TestAppContext
import io.damo.androidstarter.randomjoke.RandomJokeTabTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConnectedTestRunner {

    @get:Rule
    val activityRule: ActivityTestRule<ConnectedTestStartActivity> =
        ActivityTestRule(ConnectedTestStartActivity::class.java)

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
        startTestActivity<ConnectedTestStartActivity>()
    }

    @Test
    fun allTests() {
        val allTests = listOf(
            { AppPreferencesTest(testAppContext).testSaveJoke() },
            { AppPreferencesTest(testAppContext).testGetJoke() },

            { MainActivityTest(testAppContext).testNavigation() },
            { MainActivityTest(testAppContext).testSavingStateOnScreenRotation() },

            { RandomJokeTabTest(testAppContext).testCreation_WhenJokeIsCached() },
            { RandomJokeTabTest(testAppContext).testCreation_RefreshingTheJoke() },
            { RandomJokeTabTest(testAppContext).testCreation_OnLoadFailure() },

            { CategoriesTabTest(testAppContext).testNavigation() }
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
