package io.damo.androidstarter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import io.damo.androidstarter.support.RemoteData
import io.damo.androidstarter.support.observe
import kotlinx.android.synthetic.main.fragment_random_joke.jokeTextView
import kotlinx.android.synthetic.main.fragment_random_joke.refreshButton

class RandomJokeFragment : Fragment() {

    private val viewModel: RandomJokeViewModel by viewModels(
        ownerProducer = ::requireActivity,
        factoryProducer = { appComponent.viewModelFactory }
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_random_joke, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.joke().observe(this) { jokeData ->
            jokeTextView.text =
                when (jokeData) {
                    is RemoteData.NotLoaded -> {
                        viewModel.loadJoke()
                        refreshButton.isEnabled = false
                        "-"
                    }
                    is RemoteData.Loading -> {
                        refreshButton.isEnabled = false
                        "Loading..."
                    }
                    is RemoteData.Loaded -> {
                        refreshButton.isEnabled = true
                        jokeData.data.content
                    }
                    is RemoteData.Error -> {
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
