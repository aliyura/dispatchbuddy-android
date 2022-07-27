package com.example.dispatchbuddy.presentation.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.validation.*
import com.example.dispatchbuddy.common.validation.FieldValidationTracker.FieldType
import com.example.dispatchbuddy.databinding.FragmentChangePasswordBinding


class ChangePasswordFragment : Fragment() {
    private  var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validateFields()
        binding.fragmentChangePasswordBtn.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }
    }

    private fun validateFields() {
        val fieldTypesToValidate = listOf(
            FieldType.PASSWORD,
            FieldType.CONFIRM_PASSWORD
        )
        FieldValidationTracker.populateFieldTypeMap(fieldTypesToValidate)

        binding.apply {
            fragmentOldPasswordLayout.validateField(
                getString(R.string.enter_valid_password_str),
                FieldType.PASSWORD
            ) { input ->
                FieldValidations.verifyPassword(input)
            }
            fragmentNewPasswordLayout.validateField(
                getString(R.string.enter_valid_password_str),
                FieldType.PASSWORD
            ) { input ->
                FieldValidations.verifyPassword(input)
            }
            fragmentConfirmPasswordLayout.validateConfirmPassword(
                fragmentNewPasswordLayout, FieldType.CONFIRM_PASSWORD,
                getString(R.string.enter_valid_confirm_password_str)
            )
            fragmentChangePasswordBtn.observeFieldsValidationToEnableButton(
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