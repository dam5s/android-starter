package io.damo.androidstarter

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import io.damo.androidstarter.support.RemoteData.Error
import io.damo.androidstarter.support.RemoteData.Loaded
import io.damo.androidstarter.support.RemoteData.Loading
import io.damo.androidstarter.support.RemoteData.NotLoaded
import io.damo.androidstarter.support.observe
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: RandomJokeViewModel by viewModels { appComponent.viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.joke().observe(this) { jokeData ->
            jokeText.text =
                when (jokeData) {
                    is NotLoaded -> "-"
                    is Loading -> "Loading..."
                    is Loaded -> jokeData.data.content
                    is Error -> jokeData.explanation.message
                }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadJoke()
    }
}
