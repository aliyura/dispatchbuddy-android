package com.example.dispatchbuddy.presentation.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.databinding.FragmentWelcomeBinding
import com.example.dispatchbuddy.presentation.ui.rider_dashboard.RiderActivity
import com.example.dispatchbuddy.presentation.ui.user_dashboard.DashboardActivity

class WelcomeFragment : Fragment() {
    private  var _binding: FragmentWelcomeBinding? = null
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
    private fun buttonClickListener(){
        with(binding){
            fragmentWelcomeUserBtn.setOnClickListener {
                findNavController().navigate(R.id.homeFragment)
            }
            fragmentWelcomeRiderBtn.setOnClickListener {
                navigateToRiderDashboard()
            }
        }
    }

    private fun navigateToRiderDashboard() {
        Intent(requireContext(), RiderActivity::class.java).also {
                intentRiderActivity -> startActivity(intentRiderActivity)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}