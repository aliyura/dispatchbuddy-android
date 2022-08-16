package com.example.dispatchbuddy.presentation.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.dispatchbuddy.common.preferences.Preferences
import com.example.dispatchbuddy.presentation.ui.rider_dashboard.RiderActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class FirstScreenActivity : AppCompatActivity() {

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch(Dispatchers.Main) {
            delay(1000L)
            val pref = preferences.getToken()

            if (pref.isEmpty()) {
                val intent = Intent(this@FirstScreenActivity, AuthenticationActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this@FirstScreenActivity, RiderActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}