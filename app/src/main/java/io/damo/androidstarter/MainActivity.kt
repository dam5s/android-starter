package io.damo.androidstarter

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import io.damo.androidstarter.support.RemoteData.Error
import io.damo.androidstarter.support.RemoteData.Loaded
import io.damo.androidstarter.support.RemoteData.Loading
import io.damo.androidstarter.support.RemoteData.NotLoaded
import io.damo.androidstarter.support.observe
import kotlinx.android.synthetic.main.activity_main.jokeTextView
import kotlinx.android.synthetic.main.activity_main.refreshButton

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
            context.startActivity(intent)
        }
    }

    private val viewModel: RandomJokeViewModel by viewModels { appComponent.viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.joke().observe(this) { jokeData ->
            jokeTextView.text =
                when (jokeData) {
                    is NotLoaded -> {
                        viewModel.loadJoke()
                        refreshButton.isEnabled = false
                        "-"
                    }
                    is Loading -> {
                        refreshButton.isEnabled = false
                        "Loading..."
                    }
                    is Loaded -> {
                        refreshButton.isEnabled = true
                        jokeData.data.content
                    }
                    is Error -> {
                        refreshButton.isEnabled = true
                        jokeData.explanation.message
                    }
                }
        }

        refreshButton.setOnClickListener {
            viewModel.loadJoke()
        }
    }
}
