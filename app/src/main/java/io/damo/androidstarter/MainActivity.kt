package io.damo.androidstarter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.damo.androidstarter.support.RemoteData.Error
import io.damo.androidstarter.support.RemoteData.Loaded
import io.damo.androidstarter.support.RemoteData.Loading
import io.damo.androidstarter.support.RemoteData.NotLoaded
import io.damo.androidstarter.support.observe
import kotlinx.android.synthetic.main.activity_main.jokeText

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: RandomJokeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val factory = appComponent.viewModelFactory
        viewModel = factory.create(RandomJokeViewModel::class.java)

        observe(viewModel.joke()) { jokeData ->
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
