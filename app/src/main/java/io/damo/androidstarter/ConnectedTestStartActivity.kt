package io.damo.androidstarter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.res.stringResource

class ConnectedTestStartActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Button(onClick = { MainActivity.start(this) }) {
                Text(stringResource(R.string.start_main_activity))
            }
        }
    }
}
