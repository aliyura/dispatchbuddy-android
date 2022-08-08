package com.example.dispatchbuddy.presentation.ui.user_dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.preferences.Preferences
import com.example.dispatchbuddy.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var preferences: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.findRidersButton.setOnClickListener {
            getUserInputs()
            findNavController().navigate(R.id.ridersListFragment)
        }
    }
    private fun getUserInputs(){
        val pickup = binding.pickupLocationEt.text.toString()
        val destination = binding.deliveryDestinationEt.text.toString()
        savePickAndDestinationLocation(pickup = pickup, destination = destination)
    }
    private fun setEditTextValuesIfNull(){
        if (preferences.getDestination().isNotEmpty()) binding.deliveryDestinationEt.setText(preferences.getDestination()) else binding.deliveryDestinationEt.setText("")
        if (preferences.getPickUp().isNotEmpty()) binding.pickupLocationEt.setText(preferences.getPickUp()) else binding.pickupLocationEt.setText("")
    }

    private fun savePickAndDestinationLocation(pickup: String, destination: String) {
        savePickup(pickup)
        saveDestination(destination)
    }
    private fun savePickup(pickup: String) = preferences.savePickUp(pickup)
    private fun saveDestination(destination: String) = preferences.saveDestination(destination)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}