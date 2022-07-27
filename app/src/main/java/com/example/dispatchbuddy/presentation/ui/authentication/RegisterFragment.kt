package com.example.dispatchbuddy.presentation.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.validation.FieldValidationTracker.FieldType
import com.example.dispatchbuddy.common.validation.FieldValidationTracker.populateFieldTypeMap
import com.example.dispatchbuddy.common.validation.FieldValidations.verifyDateOfBirth
import com.example.dispatchbuddy.common.validation.FieldValidations.verifyEmail
import com.example.dispatchbuddy.common.validation.FieldValidations.verifyName
import com.example.dispatchbuddy.common.validation.FieldValidations.verifyPassword
import com.example.dispatchbuddy.common.validation.FieldValidations.verifyPhoneNumber
import com.example.dispatchbuddy.common.validation.observeFieldsValidationToEnableButton
import com.example.dispatchbuddy.common.validation.validateField
import com.example.dispatchbuddy.databinding.FragmentRegisterBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
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
        with(binding) {
            fragmentRegisterSignUpBtn.setOnClickListener {
                findNavController().navigate(R.id.smsVerificationFragment)
            }
            fragmentRegisterHaveAccountTv.setOnClickListener {
                findNavController().navigate(R.id.loginFragment)
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
                viewLifecycleOwner)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}