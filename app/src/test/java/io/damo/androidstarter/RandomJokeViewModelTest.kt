package io.damo.androidstarter

import io.damo.androidstarter.JokeApi.JokeJson
import io.damo.androidstarter.support.RemoteData.Loaded
import io.damo.androidstarter.support.RemoteData.Loading
import io.damo.androidstarter.support.RemoteData.NotLoaded
import io.damo.androidstarter.support.Success
import io.damo.androidstarter.testsupport.LiveRemoteDataAssert.Companion.assertThat
import io.damo.androidstarter.testsupport.ViewModelTest
import io.damo.androidstarter.testsupport.observeWithMock
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class RandomJokeViewModelTest : ViewModelTest() {

    private val api = mockk<JokeApi>()
    private val appPrefs = mockk<AppPreferences>(relaxUnitFun = true)

    @Test
    fun `test loadJoke() without cache`() = runBlockingTest {
        every { api.getRandomJoke() } returns Success(JokeJson(10, "Oh hai!"))
        every { appPrefs.getJoke() } returns null

        val viewModel = RandomJokeViewModel(api, appPrefs, testDispatcher)
        val viewModelData = viewModel.joke()
        val observer = observeWithMock(viewModelData)

        assertThat(viewModelData).isNotLoaded()

        viewModel.loadJoke()

        verifyOrder {
            observer.onChanged(ofType<NotLoaded<JokeView>>())
            observer.onChanged(ofType<Loading<JokeView>>())
            appPrefs.saveJoke("Oh hai!")
            observer.onChanged(Loaded(JokeView("Oh hai!")))
        }
    }

    @Test
    fun `test loadJoke() when there is a cached joke`() = runBlockingTest {
        every { api.getRandomJoke() } returns Success(JokeJson(10, "Oh hai!"))
        every { appPrefs.getJoke() } returns "Great joke"

        val viewModel = RandomJokeViewModel(api, appPrefs, testDispatcher)
        val viewModelData = viewModel.joke()
        val observer = observeWithMock(viewModelData)

        assertThat(viewModelData).isLoadedWith(JokeView("Great joke"))

        viewModel.loadJoke()

        verifyOrder {
            observer.onChanged(Loaded(JokeView("Great joke")))
            observer.onChanged(ofType<Loading<JokeView>>())
            observer.onChanged(Loaded(JokeView("Oh hai!")))
        }
    }
}
