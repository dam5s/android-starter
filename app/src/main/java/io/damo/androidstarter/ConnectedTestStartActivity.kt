package io.damo.androidstarter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.damo.androidstarter.databinding.ActivityConnectedTestStartBinding

class ConnectedTestStartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConnectedTestStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityConnectedTestStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startMainActivity.setOnClickListener {
            MainActivity.start(this)
        }
    }
}
