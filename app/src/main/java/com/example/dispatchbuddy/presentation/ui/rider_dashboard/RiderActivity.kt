package com.example.dispatchbuddy.presentation.ui.rider_dashboard

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.Constants.CHANNEL_ID
import com.example.dispatchbuddy.common.WorkerClass
import com.example.dispatchbuddy.common.WorkerClass.Companion.setUpNotification
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

        /**
         * parameters below are to notify user when theres a new dispatch request
         * it would be triggered from an observer
         * */
//        createNotificationChannel()
//        createWorkRequest()
//        showNotification()
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

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createWorkRequest() {
        //set charging constraints
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()

        //periodic work request
        val workRequest = OneTimeWorkRequest.Builder(WorkerClass::class.java)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(workRequest)

        //observe result state
        WorkManager.getInstance(applicationContext).getWorkInfoByIdLiveData(workRequest.id)
            .observe(this) { workInfo ->
//                binding.notificationResult.append(workInfo.state.name + "\n")
            }
    }

    private fun showNotification() {
        setUpNotification(this)
    }

    private fun cancelWorkRequest() {
        WorkManager.getInstance(applicationContext).cancelAllWork()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        cancelWorkRequest()
    }
}