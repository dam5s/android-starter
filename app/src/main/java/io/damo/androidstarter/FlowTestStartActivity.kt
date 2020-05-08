package io.damo.androidstarter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_flow_test_start.startMainActivity

class FlowTestStartActivity : AppCompatActivity(R.layout.activity_flow_test_start) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startMainActivity.setOnClickListener {
            MainActivity.start(this)
        }
    }
}
