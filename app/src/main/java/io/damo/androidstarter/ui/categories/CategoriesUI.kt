package io.damo.androidstarter.ui.categories

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import io.damo.androidstarter.AppLifeCycle
import io.damo.androidstarter.R
import io.damo.androidstarter.prelude.Redux

@Composable
fun CategoriesUI(stateStore: Redux.Store<AppLifeCycle.State>) {
    Text(text = stringResource(R.string.categories))
}
