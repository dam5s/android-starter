package io.damo.androidstarter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_connected_test_start.startMainActivity

class ConnectedTestStartActivity : AppCompatActivity(R.layout.activity_connected_test_start) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startMainActivity.setOnClickListener {
            MainActivity.start(this)
        }
    }
}
