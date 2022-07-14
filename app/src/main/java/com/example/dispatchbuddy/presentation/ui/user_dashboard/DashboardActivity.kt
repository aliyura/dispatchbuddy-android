package com.example.dispatchbuddy.presentation.ui.user_dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.dispatchbuddy.R

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val navController = findNavController(R.id.nav_host_fragment_content_dashboard)
    }
}