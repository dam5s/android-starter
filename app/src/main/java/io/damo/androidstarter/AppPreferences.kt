package io.damo.androidstarter

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class AppPreferences(context: Context) {

    val prefsFile = "io.damo.AppPreferences"
    val jokeKey = "Joke"

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(prefsFile, Context.MODE_PRIVATE)
    }

    fun saveJoke(jokeText: String) =
        sharedPreferences.edit { putString(jokeKey, jokeText) }

    fun getJoke(): String? =
        sharedPreferences.getString(jokeKey, null)

    fun clear() =
        sharedPreferences.edit { clear() }
}
