package io.damo.androidstarter.testsupport

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import io.damo.androidstarter.support.LiveRemoteData
import io.damo.androidstarter.support.RemoteData
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
open class ViewModelTest {

    @get:Rule
    val instantExecRule = InstantTaskExecutorRule()

    protected val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
    }
}

typealias RemoteDataObserver<T> = Observer<RemoteData<T>>

fun <T> observeWithMock(data: LiveRemoteData<T>): RemoteDataObserver<T> =
    mockk<RemoteDataObserver<T>>(relaxed = true)
        .also { data.observeForever(it) }
