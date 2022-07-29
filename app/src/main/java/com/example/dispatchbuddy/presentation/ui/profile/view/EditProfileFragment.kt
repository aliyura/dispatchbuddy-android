package com.example.dispatchbuddy.presentation.ui.profile.view

import android.os.Bundle
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.Constants.GALLERY
import com.example.dispatchbuddy.common.Constants.GALLERY_PERMISSION_CODE
import com.example.dispatchbuddy.common.Constants.dummyToken
import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.ViewExtensions.showShortSnackBar
import com.example.dispatchbuddy.common.validation.FieldValidationTracker
import com.example.dispatchbuddy.common.validation.FieldValidations
import com.example.dispatchbuddy.common.validation.observeFieldsValidationToEnableButton
import com.example.dispatchbuddy.common.validation.validateField
import com.example.dispatchbuddy.data.remote.dto.models.UpdateProfile
import com.example.dispatchbuddy.databinding.FragmentEditProfileBinding
import com.example.dispatchbuddy.presentation.ui.profile.viewmodel.ProfileViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()

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
        validateFields()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observerUpdateProfileResponse()
        binding.fragmentEditProfileSaveBtn.setOnClickListener {
            updateUserProfile()
        }
        binding.fragmentRegisterCalenderEdt.setOnClickListener {
            datePicker()
        }

    }
    private fun setUpDropdownMenu(){
        val genderType = resources.getStringArray(R.array.Gender)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, genderType)
        binding.selectGenderDropdown.setAdapter(arrayAdapter)
    }

    private fun updateUserProfile(){
        with(binding){
            val fullName = fragmentEditFullNameEdt.text.toString()
            val country = fragmentEditCountryEdt.text.toString()
            val city = fragmentEditCityEdt.text.toString()
            val gender = selectGenderDropdown.text.toString()
            val dateOfBirth = fragmentRegisterCalenderEdt.text.toString()
            profileViewModel.updateProfile(UpdateProfile(
                name = fullName,
                country = country,
                gender = gender,
                dateOfBirth = dateOfBirth,
                city = city
            ), "Bearer $dummyToken")
        }
    }

    private fun datePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker().build()
        datePicker.show(parentFragmentManager, "Select Date")
        datePicker.addOnPositiveButtonClickListener {
            val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
            val date = dateFormatter.format(Date(it))
            binding.fragmentRegisterCalenderEdt.setText(date)
        }
    }

    private fun observerUpdateProfileResponse(){
        lifecycleScope.launch {
            profileViewModel.editProfileResponse.collect{ response ->
                when(response){
                    is Resource.Loading ->{
                        binding.editProfileProgressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success ->{
                        binding.editProfileProgressBar.visibility = View.GONE
                        showShortSnackBar(response.value.message)
                        Log.d("NAME", "USER_NAME EDIT_PROFILE-->:${response.value.payload.name} ")
                        findNavController().navigate(R.id.profileFragment)
                    }
                    is Resource.Error ->{
                        binding.editProfileProgressBar.visibility = View.GONE
                        showShortSnackBar(response.error)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun validateFields() {
        val fieldTypesToValidate = listOf(
            FieldValidationTracker.FieldType.FULLNAME,
            FieldValidationTracker.FieldType.COUNTRY,
            FieldValidationTracker.FieldType.CITY,
            FieldValidationTracker.FieldType.GENDER,
            FieldValidationTracker.FieldType.DATEOFBIRTH,
        )
        FieldValidationTracker.populateFieldTypeMap(fieldTypesToValidate)

        binding.apply {
            fragmentEditFullNameLayout.validateField(
                getString(R.string.enter_valid_name_str),
                FieldValidationTracker.FieldType.FULLNAME
            ) { input ->
                FieldValidations.verifyName(input)
            }
            fragmentEditCountryLayout.validateField(
                getString(R.string.enter_valid_country),
                FieldValidationTracker.FieldType.COUNTRY
            ) { input ->
                FieldValidations.verifyCountry(input)
            }
            fragmentEditCityLayout.validateField(
                getString(R.string.enter_valid_city),
                FieldValidationTracker.FieldType.CITY
            ) { input ->
                FieldValidations.verifyCity(input)
            }
            selectGenderDropdownLayout.validateField(
                getString(R.string.select_an_option),
                FieldValidationTracker.FieldType.GENDER
            ) { input ->
                FieldValidations.validateGender(input)
            }
            fragmentEditCalenderLayout.validateField(
                getString(R.string.enter_valid_date_of_birth_str),
                FieldValidationTracker.FieldType.DATEOFBIRTH
            ) { input ->
                FieldValidations.verifyDateOfBirth(input)
            }

            fragmentEditProfileSaveBtn.observeFieldsValidationToEnableButton(
                requireContext(),
                viewLifecycleOwner
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}