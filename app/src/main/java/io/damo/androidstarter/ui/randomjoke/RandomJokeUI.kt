package io.damo.androidstarter.ui.randomjoke

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.damo.androidstarter.AppLifeCycle.State
import io.damo.androidstarter.R
import io.damo.androidstarter.backend.RemoteData.Error
import io.damo.androidstarter.backend.RemoteData.Loaded
import io.damo.androidstarter.backend.RemoteData.Loading
import io.damo.androidstarter.backend.RemoteData.NotLoaded
import io.damo.androidstarter.backend.RemoteData.Refreshing
import io.damo.androidstarter.prelude.Redux
import io.damo.androidstarter.prelude.stateOf

@Composable
private fun MessageCard(message: String, textColor: Color, background: Color) {
    val shapes = MaterialTheme.shapes
    val typo = MaterialTheme.typography

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = shapes.small,
        elevation = 4.dp,
    ) {
        Box(modifier = Modifier.background(background)) {
            Text(
                modifier = Modifier.padding(16.dp),
                color = textColor,
                text = message,
                style = typo.body1,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun JokeCard(view: RandomJokeView) {
    val colors = MaterialTheme.colors

    MessageCard(
        message = view.content,
        textColor = colors.onSecondary,
        background = colors.secondaryVariant.copy(alpha = .5f)
    )
}

@Composable
private fun LoadingBox() {
    Box(Modifier.padding(32.dp)) {
        CircularProgressIndicator(modifier = Modifier.size(32.dp))
    }
}

@Composable
private fun NotLoadedUI() {
    // nothing
}

@Composable
private fun LoadingUI() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoadingBox()
    }
}

@Composable
private fun LoadedUI(view: RandomJokeView) {
    JokeCard(view)
}

@Composable
private fun RefreshingUI(view: RandomJokeView) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        JokeCard(view)
        LoadingBox()
    }
}

@Composable
private fun ErrorUI() {
    val colors = MaterialTheme.colors

    MessageCard(
        message = stringResource(R.string.generic_error),
        textColor = colors.onError,
        background = colors.error.copy(alpha = .5f)
    )
}

@Composable
fun RandomJokeUI(stateStore: Redux.Store<State>) {
    val randomJoke = remember { stateStore.stateOf { it.randomJoke } }

    when (val remoteData = randomJoke.value) {
        is NotLoaded -> NotLoadedUI()
        is Loaded -> LoadedUI(remoteData.data)
        is Loading -> LoadingUI()
        is Refreshing -> RefreshingUI(remoteData.data)
        is Error -> ErrorUI()
    }
}
