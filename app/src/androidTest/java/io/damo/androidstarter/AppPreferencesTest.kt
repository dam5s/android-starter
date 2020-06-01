package io.damo.androidstarter

import android.content.Context
import android.content.SharedPreferences
import io.damo.androidstarter.instrumentationsupport.TestAppContext
import org.assertj.core.api.Assertions.assertThat

class AppPreferencesTest(testAppContext: TestAppContext) {

    private val app = testAppContext.app
    private val appPrefs = testAppContext.testComponent.appPreferences

    private val sharedPreferences: SharedPreferences =
        app.getSharedPreferences(appPrefs.prefsFile, Context.MODE_PRIVATE)

    fun testSaveJoke() {
        appPrefs.saveJoke("Oh hello")

        val savedJoke = sharedPreferences.getString(appPrefs.jokeKey, "Failed!")

        assertThat(savedJoke).isEqualTo("Oh hello")
    }

    fun testGetJoke() {
        assertThat(appPrefs.getJoke()).isNull()

        appPrefs.saveJoke("Oh hello")

        assertThat(appPrefs.getJoke()).isEqualTo("Oh hello")
    }
}
