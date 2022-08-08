package com.example.dispatchbuddy.presentation.ui.rider_dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.databinding.ActivityRiderBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RiderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRiderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNavigation()
    }

    private fun initBottomNavigation() {
        val bottomNavView: BottomNavigationView = binding.bottomNavView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_dashboard) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavView.setupWithNavController(navController)
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment,
                R.id.registerFragment,
                R.id.changePasswordFragment,
                R.id.editProfileFragment,
                R.id.deliveriesFragment-> {
                    hideBottomNav()
                }
                else -> {
                    showBottomNav()
                }
            }
        }
    }

    private fun hideBottomNav() {
        binding.bottomNavView.visibility = View.GONE
    }

    private fun showBottomNav() {
        binding.bottomNavView.visibility = View.VISIBLE
    }
}