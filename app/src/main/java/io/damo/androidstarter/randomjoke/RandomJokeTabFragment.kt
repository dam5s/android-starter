package io.damo.androidstarter.randomjoke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.damo.androidstarter.R
import io.damo.androidstarter.appComponent
import io.damo.androidstarter.backend.RemoteData
import io.damo.androidstarter.backend.RemoteData.Error
import io.damo.androidstarter.backend.RemoteData.Loaded
import io.damo.androidstarter.backend.RemoteData.Loading
import io.damo.androidstarter.backend.RemoteData.NotLoaded
import io.damo.androidstarter.databinding.FragmentRandomJokeTabBinding
import io.damo.androidstarter.joke.JokeView
import io.damo.androidstarter.prelude.Subscriber
import io.damo.androidstarter.stateStore

class RandomJokeTabFragment : Fragment(), Subscriber<RemoteData<JokeView>> {

    private var binding: FragmentRandomJokeTabBinding? = null

    private val swipeRefresh get() = binding?.swipeRefresh
    private val jokeTextView get() = binding?.jokeTextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRandomJokeTabBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.title = getString(R.string.random_title)

        stateStore.subscribe(this) { it.randomJoke }

        swipeRefresh?.setOnRefreshListener {
            loadJoke()
        }
    }

    override fun onDestroyView() {
        stateStore.unsubscribe(this)
        super.onDestroyView()
    }

    override fun onStateChanged(state: RemoteData<JokeView>) {
        loadJokeIfNeeded(state)
        updateJokeTextView(state)
        updateSwipeRefresh(state)
    }

    private fun updateSwipeRefresh(jokeData: RemoteData<JokeView>) {
        if (jokeData is Loading) return
        swipeRefresh?.isRefreshing = false
    }

    private fun updateJokeTextView(jokeData: RemoteData<JokeView>) {
        jokeTextView?.text =
            when (jokeData) {
                is NotLoaded -> ""
                is Loading -> context?.getString(R.string.loading) ?: ""
                is Loaded -> jokeData.data.content
                is Error -> jokeData.error.message()
            }
    }

    private fun loadJokeIfNeeded(jokeData: RemoteData<JokeView>) {
        if (jokeData is NotLoaded) loadJoke()
    }

    private fun loadJoke() = loadJoke(stateStore, appComponent.jokeApi)
}
