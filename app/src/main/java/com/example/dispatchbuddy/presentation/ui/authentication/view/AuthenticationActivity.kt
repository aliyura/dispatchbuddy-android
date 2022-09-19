package com.example.dispatchbuddy.presentation.ui.authentication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.databinding.ActivityAuthenticationBinding
import com.example.dispatchbuddy.databinding.ActivityRiderBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}