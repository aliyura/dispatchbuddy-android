package com.example.dispatchbuddy.presentation.ui.rider_dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.databinding.ActivityRiderBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class RiderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRiderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavView: BottomNavigationView = binding.bottomNavView
        val navController = findNavController(R.id.nav_host_fragment_content_dashboard)

        bottomNavView.setupWithNavController(navController)

    }
}