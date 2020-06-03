package io.damo.androidstarter.randomjoke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.get
import io.damo.androidstarter.R
import io.damo.androidstarter.activityViewModelProvider
import io.damo.androidstarter.support.RemoteData
import io.damo.androidstarter.support.RemoteData.Error
import io.damo.androidstarter.support.RemoteData.Loaded
import io.damo.androidstarter.support.RemoteData.Loading
import io.damo.androidstarter.support.RemoteData.NotLoaded
import io.damo.androidstarter.support.observe
import kotlinx.android.synthetic.main.fragment_random_joke_tab.jokeTextView
import kotlinx.android.synthetic.main.fragment_random_joke_tab.swipeRefresh

class RandomJokeTabFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.fragment_random_joke_tab, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel = activityViewModelProvider.get<RandomJokeViewModel>()

        viewModel.joke().observe(this) { jokeData ->
            loadJokeIfNeeded(jokeData, viewModel)
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
                is Loaded -> jokeData.data.content
                is Error -> jokeData.explanation.message
            }
    }

    private fun loadJokeIfNeeded(jokeData: RemoteData<JokeView>, viewModel: RandomJokeViewModel) {
        if (jokeData is NotLoaded)
            viewModel.loadJoke()
    }
}
