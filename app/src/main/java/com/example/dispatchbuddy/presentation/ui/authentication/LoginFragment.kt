package com.example.dispatchbuddy.presentation.ui.authentication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.databinding.FragmentLoginBinding
import com.example.dispatchbuddy.presentation.ui.rider_dashboard.RiderActivity

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            fragmentLoginNoAccountTv.setOnClickListener {
                findNavController().navigate(R.id.registerFragment)
            }
            fragmentLoginLoginBtn.setOnClickListener {
                Intent(requireContext(), RiderActivity::class.java).also { intent ->
                    startActivity(intent)
                }
            }
            fragmentLoginForgotPasswordTv.setOnClickListener {
                findNavController().navigate(R.id.changePasswordFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}