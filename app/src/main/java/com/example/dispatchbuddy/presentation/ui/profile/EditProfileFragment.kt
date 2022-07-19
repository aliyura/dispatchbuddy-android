package com.example.dispatchbuddy.presentation.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setUpDropdownMenu()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentEditProfileSaveBtn.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }
    }
    private fun setUpDropdownMenu(){
        val genderType = resources.getStringArray(R.array.Gender)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, genderType)
        binding.selectGenderDropdown.setAdapter(arrayAdapter)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}