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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.damo.androidstarter.AppLifeCycle.Action
import io.damo.androidstarter.AppLifeCycle.State
import io.damo.androidstarter.AppLifeCycle.Tab
import io.damo.androidstarter.R
import io.damo.androidstarter.prelude.Redux
import io.damo.androidstarter.prelude.stateOf

typealias Dispatch = (Action) -> Unit

@Composable
fun MainUI(stateStore: Redux.Store<State>) {
    val currentTab = remember { stateStore.stateOf { it.tab } }

    MaterialTheme {
        Scaffold(
            topBar = { TopBar(currentTab.value) },
            bottomBar = { BottomBar(currentTab.value, stateStore::dispatch) },
            content = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = "Hello World"
                )
            }
        )
    }
}

data class TabView(
    val tab: Tab,
    val order: Int,
    val fillRatio: Float,
    val selected: Boolean,
    @StringRes val title: Int,
    @StringRes val name: Int,
    val icon: ImageVector
)

@StringRes
fun tabTitle(tab: Tab): Int =
    when (tab) {
        Tab.Random -> R.string.random_title
        Tab.Categories -> R.string.categories_title
    }

fun tabView(tab: Tab, selected: Tab): TabView =
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
fun TopBar(currentTab: Tab) {

    val typo = MaterialTheme.typography.h2
    val colors = MaterialTheme.colors

    Surface(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            fontSize = typo.fontSize,
            fontWeight = typo.fontWeight,
            color = colors.primary,
            text = stringResource(tabTitle(currentTab)),
        )
    }
}

@Composable
fun BottomBar(currentTab: Tab, dispatch: Dispatch) {
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
fun Tab(tab: TabView, dispatch: Dispatch) {
    androidx.compose.material.Tab(
        modifier = Modifier
            .fillMaxWidth(tab.fillRatio)
            .scale(if (tab.selected) 1.1f else .9f),
        selected = tab.selected,
        onClick = { dispatch(Action.SelectTab(tab.tab)) },
        icon = { Icon(tab.icon, stringResource(tab.name), tint = MaterialTheme.colors.onPrimary) },
        text = { Text(stringResource(tab.name), color = MaterialTheme.colors.onPrimary) }
    )
}
