package com.example.dispatchbuddy.presentation.ui.rider_dashboard.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dispatchbuddy.common.locationResultList
import com.example.dispatchbuddy.databinding.FragmentLocationBootomSheetDialogBinding

class LocationBottomSheetDialog : Fragment() {
    private var _binding: FragmentLocationBootomSheetDialogBinding? = null
    private val binding get() = _binding!!
    lateinit var locationResultAdapter: LocationResultAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLocationBootomSheetDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationResultAdapter = LocationResultAdapter {  }
        binding.locationResultRv.adapter = locationResultAdapter
        locationResultAdapter.submitList(locationResultList)
    }

}