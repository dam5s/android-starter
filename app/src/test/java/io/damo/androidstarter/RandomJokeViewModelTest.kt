package io.damo.androidstarter

import io.damo.androidstarter.JokeApi.JokeJson
import io.damo.androidstarter.support.LiveRemoteDataAssert.Companion.assertThat
import io.damo.androidstarter.support.RemoteData.Loaded
import io.damo.androidstarter.support.RemoteData.Loading
import io.damo.androidstarter.support.Success
import io.damo.androidstarter.support.ViewModelTest
import io.damo.androidstarter.support.observeWithMock
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class RandomJokeViewModelTest : ViewModelTest() {

    private val api = mockk<JokeApi>()

    @Test
    fun testLoadJoke() = runBlockingTest {
        every { api.getRandomJoke() } returns Success(JokeJson(10, "Oh hai!"))

        val viewModel = RandomJokeViewModel(api, testDispatcher)
        val viewModelData = viewModel.joke()
        val observer = observeWithMock(viewModelData)

        assertThat(viewModelData).isNotLoaded()

        viewModel.loadJoke()

        verifyOrder {
            observer.onChanged(ofType<Loading<JokeView>>())
            observer.onChanged(Loaded(JokeView("Oh hai!")))
        }
    }
}
