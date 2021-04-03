package io.damo.androidstarter.randomjoke

import io.damo.androidstarter.AppLifeCycle
import io.damo.androidstarter.backend.JokeApi
import io.damo.androidstarter.backend.JokeJson
import io.damo.androidstarter.backend.RemoteData
import io.damo.androidstarter.backend.RemoteDataAssert.Companion.assertThat
import io.damo.androidstarter.ui.randomjoke.RandomJokeView
import io.damo.androidstarter.prelude.Async
import io.damo.androidstarter.prelude.Redux
import io.damo.androidstarter.prelude.Success
import io.damo.androidstarter.ui.randomjoke.RandomJokeInteractions
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.awaitility.kotlin.await
import org.awaitility.kotlin.until
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class RandomJokeTabInteractionsTest {

    private val api = mockk<JokeApi>()
    private val store = Redux.Store(AppLifeCycle.State(), AppLifeCycle::reducer)

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Default)
    }

    @Test
    fun `test loadJoke()`() {
        every { api.getRandomJokeAsync() } returns Async { Success(JokeJson(10, "Oh hai!")) }

        RandomJokeInteractions.load(store::dispatch, api)

        await until {
            store.state.randomJoke is RemoteData.Loaded
        }

        assertThat(store.state.randomJoke).isLoadedWith(RandomJokeView("Oh hai!"))
    }
}
