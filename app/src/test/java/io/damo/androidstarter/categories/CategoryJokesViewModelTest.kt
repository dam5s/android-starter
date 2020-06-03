package io.damo.androidstarter.categories

import io.damo.androidstarter.backend.JokeApi
import io.damo.androidstarter.backend.JokeJson
import io.damo.androidstarter.randomjoke.JokeView
import io.damo.androidstarter.support.RemoteData.Loaded
import io.damo.androidstarter.support.RemoteData.Loading
import io.damo.androidstarter.support.RemoteData.NotLoaded
import io.damo.androidstarter.support.Success
import io.damo.androidstarter.testsupport.LiveRemoteDataAssert.Companion.assertThat
import io.damo.androidstarter.testsupport.ViewModelTest
import io.damo.androidstarter.testsupport.observeWithMock
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

@ExperimentalCoroutinesApi
class CategoryJokesViewModelTest : ViewModelTest() {

    private val api = mockk<JokeApi>()

    @Test
    fun test() = runBlockingTest {
        every { api.getJokesForCategory(any()) } returns Success(
            listOf(
                JokeJson(10, "Oh hai!"),
                JokeJson(11, "Hello again")
            )
        )

        val viewModel = CategoryJokesViewModel(api, testDispatcher)
        val viewModelData = viewModel.jokes("my-category")
        val observer = observeWithMock(viewModelData)

        assertThat(viewModelData).isNotLoaded()

        viewModel.loadCategory("my-category")

        verifyOrder {
            observer.onChanged(ofType<NotLoaded<List<JokeView>>>())
            observer.onChanged(ofType<Loading<List<JokeView>>>())
            api.getJokesForCategory("my-category")
            observer.onChanged(
                Loaded(
                    listOf(
                        JokeView("Oh hai!"),
                        JokeView("Hello again")
                    )
                )
            )
        }
    }

    @Test
    fun `test with multiple categories`() {
        every { api.getJokesForCategory("my-category") } returns Success(
            listOf(
                JokeJson(10, "Oh hai!"),
                JokeJson(11, "Hello again")
            )
        )
        every { api.getJokesForCategory("my-other-category") } returns Success(
            listOf(
                JokeJson(12, "Wassup")
            )
        )

        val viewModel = CategoryJokesViewModel(api, testDispatcher)

        val myCategoryViewModelData = viewModel.jokes("my-category")
        val myCategoryObserver = observeWithMock(myCategoryViewModelData)

        val myOtherCategoryViewModelData = viewModel.jokes("my-other-category")
        val myOtherCategoryObserver = observeWithMock(myOtherCategoryViewModelData)

        val expectedMyCategoryJokes = listOf(JokeView("Oh hai!"), JokeView("Hello again"))
        val expectedMyOtherCategoryJokes = listOf(JokeView("Wassup"))

        viewModel.loadCategory("my-category")
        verify {
            myCategoryObserver.onChanged(Loaded(expectedMyCategoryJokes))
        }
        verify(exactly = 0) {
            myOtherCategoryObserver.onChanged(ofType<Loaded<List<JokeView>>>())
        }

        viewModel.loadCategory("my-other-category")
        verify {
            myOtherCategoryObserver.onChanged(Loaded(expectedMyOtherCategoryJokes))
        }
        verify(exactly = 0) {
            myCategoryObserver.onChanged(Loaded(expectedMyOtherCategoryJokes))
        }
    }
}
