package io.damo.androidstarter.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.damo.androidstarter.AppContainer
import io.damo.androidstarter.AppLifeCycle.Action
import io.damo.androidstarter.AppLifeCycle.State
import io.damo.androidstarter.AppLifeCycle.Tab
import io.damo.androidstarter.Dispatch
import io.damo.androidstarter.R
import io.damo.androidstarter.appContainer
import io.damo.androidstarter.backend.JokeApi
import io.damo.androidstarter.prelude.Redux
import io.damo.androidstarter.prelude.stateOf
import io.damo.androidstarter.ui.categories.CategoriesUI
import io.damo.androidstarter.ui.randomjoke.RandomJokeInteractions
import io.damo.androidstarter.ui.randomjoke.RandomJokeUI

@Composable
fun appContainer(): AppContainer {
    LocalConfiguration.current
    return LocalContext.current.appContainer
}

private data class TabView(
    val tab: Tab,
    val order: Int,
    val fillRatio: Float,
    val selected: Boolean,
    @StringRes val title: Int,
    @StringRes val name: Int,
    val icon: ImageVector
)

@StringRes
private fun tabTitle(tab: Tab): Int =
    when (tab) {
        Tab.Random -> R.string.random_title
        Tab.Categories -> R.string.categories_title
    }

private fun tabView(tab: Tab, selected: Tab): TabView =
    when (tab) {
        Tab.Random -> TabView(
            tab = tab,
            order = 1,
            fillRatio = 1 / 2f,
            selected = tab == selected,
            title = tabTitle(tab),
            name = R.string.random,
            icon = Icons.Filled.Home
        )
        Tab.Categories -> TabView(
            tab = tab,
            order = 2,
            fillRatio = 1f,
            selected = tab == selected,
            title = tabTitle(tab),
            name = R.string.categories,
            icon = Icons.Filled.FileCopy
        )
    }

@Composable
private fun TopBar(currentTab: Tab) {

    val typo = MaterialTheme.typography
    val colors = MaterialTheme.colors

    Surface(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp, end = 64.dp),
            style = typo.h2,
            color = colors.primary,
            text = stringResource(tabTitle(currentTab)),
        )
    }
}

fun selectTab(tab: Tab, api: JokeApi, dispatch: Dispatch): () -> Unit =
    when (tab) {
        Tab.Random -> {
            {
                RandomJokeInteractions.load(dispatch, api)
                dispatch(Action.SelectTab(tab))
            }
        }
        Tab.Categories -> {
            { dispatch(Action.SelectTab(tab)) }
        }
    }

@Composable
private fun Tab(tab: TabView, dispatch: Dispatch) {
    val api = appContainer().jokeApi

    androidx.compose.material.Tab(
        modifier = Modifier
            .fillMaxWidth(tab.fillRatio)
            .scale(if (tab.selected) 1.1f else .8f),
        selected = tab.selected,
        onClick = selectTab(tab.tab, api, dispatch),
        icon = { Icon(tab.icon, stringResource(tab.name), tint = MaterialTheme.colors.onPrimary) },
        text = { Text(stringResource(tab.name), color = MaterialTheme.colors.onPrimary) }
    )
}

@Composable
private fun BottomBar(currentTab: Tab, dispatch: Dispatch) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.primary,
        elevation = 8.dp
    ) {
        Row {
            Tab.values()
                .map { tabView(it, currentTab) }
                .sortedBy { it.order }
                .forEach { Tab(it, dispatch) }
        }
    }
}

@Composable
private fun TabContent(currentTab: Tab, stateStore: Redux.Store<State>) {
    when (currentTab) {
        Tab.Random -> RandomJokeUI(stateStore)
        Tab.Categories -> CategoriesUI(stateStore)
    }
}

@Composable
fun MainUI(stateStore: Redux.Store<State>) {
    val currentTab = remember { stateStore.stateOf { it.tab } }

    MaterialTheme {
        Scaffold(
            topBar = { TopBar(currentTab.value) },
            bottomBar = { BottomBar(currentTab.value, stateStore::dispatch) },
            content = { TabContent(currentTab.value, stateStore) }
        )
    }
}
