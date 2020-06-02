package io.damo.androidstarter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import io.damo.androidstarter.support.RemoteData
import io.damo.androidstarter.support.RemoteData.Loading
import io.damo.androidstarter.support.RemoteData.NotLoaded
import io.damo.androidstarter.support.observe
import kotlinx.android.synthetic.main.fragment_random_joke.jokeTextView
import kotlinx.android.synthetic.main.fragment_random_joke.swipeRefresh

class RandomJokeFragment : Fragment() {

    private val viewModel: RandomJokeViewModel by viewModels(
        ownerProducer = ::requireActivity,
        factoryProducer = { appComponent.viewModelFactory }
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_random_joke, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.joke().observe(this) { jokeData ->
            loadJokeIfNeeded(jokeData)
            updateJokeTextView(jokeData)
            updateSwipeRefresh(jokeData)
        }

        swipeRefresh.setOnRefreshListener {
            viewModel.loadJoke()
        }
    }

    private fun updateSwipeRefresh(jokeData: RemoteData<JokeView>) {
        if (jokeData is Loading) return
        swipeRefresh.isRefreshing = false
    }

    private fun updateJokeTextView(jokeData: RemoteData<JokeView>) {
        jokeTextView.text =
            when (jokeData) {
                is NotLoaded -> "-"
                is Loading -> "Loading..."
                is RemoteData.Loaded -> jokeData.data.content
                is RemoteData.Error -> jokeData.explanation.message
            }
    }

    private fun loadJokeIfNeeded(jokeData: RemoteData<JokeView>) {
        if (jokeData is NotLoaded) {
            viewModel.loadJoke()
        }
    }
}
