package com.example.dispatchbuddy.presentation.ui.authentication.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.databinding.FragmentWelcomeBinding
import com.example.dispatchbuddy.presentation.ui.user_dashboard.view.UserActivity

class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonClickListener()
    }

    private fun buttonClickListener() {
        with(binding) {
            fragmentWelcomeCardOne.setOnClickListener {
                navigateToUserDashboard()
            }
            fragmentWelcomeCardTwo.setOnClickListener {
                findNavController().navigate(R.id.loginFragment)
            }
        }
    }

    private fun navigateToUserDashboard() {
        Intent(requireContext(), UserActivity::class.java).also { intentUserActivity ->
            startActivity(intentUserActivity)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}