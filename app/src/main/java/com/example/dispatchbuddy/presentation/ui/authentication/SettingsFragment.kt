package com.example.dispatchbuddy.presentation.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.databinding.FragmentLoginBinding
import com.example.dispatchbuddy.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentSettingsLoginBtn.setOnClickListener {
           findNavController().navigate(R.id.loginFragment)
        }
        binding.fragmentSettingsRegisterBtn.setOnClickListener {
            findNavController().navigate(R.id.registerFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}