package com.example.dispatchbuddy.presentation.ui.authentication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.ViewExtensions.hideView
import com.example.dispatchbuddy.common.ViewExtensions.showShortSnackBar
import com.example.dispatchbuddy.common.ViewExtensions.showView
import com.example.dispatchbuddy.common.popBackStack
import com.example.dispatchbuddy.common.preferences.Preferences
import com.example.dispatchbuddy.common.validation.FieldValidationTracker.FieldType
import com.example.dispatchbuddy.common.validation.FieldValidationTracker.populateFieldTypeMap
import com.example.dispatchbuddy.common.validation.FieldValidations.verifyDateOfBirth
import com.example.dispatchbuddy.common.validation.FieldValidations.verifyEmail
import com.example.dispatchbuddy.common.validation.FieldValidations.verifyName
import com.example.dispatchbuddy.common.validation.FieldValidations.verifyPassword
import com.example.dispatchbuddy.common.validation.FieldValidations.verifyPhoneNumber
import com.example.dispatchbuddy.common.validation.observeFieldsValidationToEnableButton
import com.example.dispatchbuddy.common.validation.validateField
import com.example.dispatchbuddy.data.remote.dto.models.Registration
import com.example.dispatchbuddy.databinding.FragmentRegisterBinding
import com.example.dispatchbuddy.presentation.ui.authentication.viewmodel.RegistrationViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val TAG = "RegisterFragment"
    var email: String = ""
    private val registrationViewModel: RegistrationViewModel by viewModels()

    @Inject
    lateinit var preferences: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeRegistrationResponse()

        with(binding) {
            fragmentRegisterSignUpBtn.setOnClickListener {
                registerUser()
            }
            fragmentRegisterHaveAccountTv.setOnClickListener {
                val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                findNavController().navigate(action)
            }
            fragmentRegisterCalenderEdt.setOnClickListener {
                datePicker()
            }
        }
        validateFields()
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

    private fun registerUser() {
        with(binding) {
            val fullName = fragmentRegisterFullNameEdt.text.toString()
            email = fragmentRegisterEmailEdt.text.toString()
            val phoneNumber = fragmentRegisterPhoneNumberEdt.text.toString()
            val dateOfBirth = fragmentRegisterCalenderEdt.text.toString()
            val password = fragmentRegisterPasswordEdt.text.toString()

            registrationViewModel.registerUser(
                Registration(
                    authProvider = "EMAIL",
                    name = fullName,
                    email = email,
                    phoneNumber = phoneNumber,
                    dateOfBirth = dateOfBirth,
                    password = password
                )
            )
        }
    }

    private fun observeRegistrationResponse() {
        lifecycleScope.launch {
            registrationViewModel.stateFlow.collect {
                when (it) {
                    is Resource.Loading -> {
                        binding.loader.showView()
                    }
                    is Resource.Success -> {
                        binding.loader.hideView()
                        showShortSnackBar(it.value.message)
                        if (it.value.success){
                            val responseEmail = binding.fragmentRegisterEmailEdt.text.toString()
                            preferences.saveEmail(responseEmail)
                            val action =
                                RegisterFragmentDirections.actionRegisterFragmentToSmsVerificationFragment(
                                    responseEmail, "registration"
                                )
                            findNavController().navigate(action)
                        }
                    }
                    is Resource.Error -> {
                        binding.loader.hideView()
                        showShortSnackBar(it.error)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun validateFields() {
        val fieldTypesToValidate = listOf(
            FieldType.FULLNAME,
            FieldType.PHONENUMBER,
            FieldType.EMAIL,
            FieldType.DATEOFBIRTH,
            FieldType.PASSWORD
        )
        populateFieldTypeMap(fieldTypesToValidate)

        binding.apply {
            fragmentRegisterFullNameLayout.validateField(
                getString(R.string.enter_valid_name_str),
                FieldType.FULLNAME
            ) { input ->
                verifyName(input)
            }
            fragmentRegisterEmailLayout.validateField(
                getString(R.string.enter_valid_email_str),
                FieldType.EMAIL
            ) { input ->
                verifyEmail(input)
            }
            fragmentRegisterPhoneNumberLayout.validateField(
                getString(R.string.enter_valid_phone_number_str),
                FieldType.PHONENUMBER
            ) { input ->
                verifyPhoneNumber(input)
            }
            fragmentRegisterCalenderLayout.validateField(
                getString(R.string.enter_valid_date_of_birth_str),
                FieldType.DATEOFBIRTH
            ) { input ->
                verifyDateOfBirth(input)
            }
            fragmentRegisterPasswordLayout.validateField(
                getString(R.string.enter_valid_password_str),
                FieldType.PASSWORD
            ) { input ->
                verifyPassword(input)
            }
            fragmentRegisterSignUpBtn.observeFieldsValidationToEnableButton(
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