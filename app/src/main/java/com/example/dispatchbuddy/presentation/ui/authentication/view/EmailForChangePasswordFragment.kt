package com.example.dispatchbuddy.presentation.ui.authentication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.ViewExtensions.hideKeyBoard
import com.example.dispatchbuddy.common.ViewExtensions.hideView
import com.example.dispatchbuddy.common.ViewExtensions.showShortSnackBar
import com.example.dispatchbuddy.common.ViewExtensions.showView
import com.example.dispatchbuddy.common.preferences.Preferences
import com.example.dispatchbuddy.common.validation.FieldValidationTracker
import com.example.dispatchbuddy.common.validation.FieldValidations
import com.example.dispatchbuddy.common.validation.observeFieldsValidationToEnableButton
import com.example.dispatchbuddy.common.validation.validateField
import com.example.dispatchbuddy.databinding.FragmentEmailForChangePasswordBinding
import com.example.dispatchbuddy.presentation.ui.authentication.viewmodel.VerificationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EmailForChangePasswordFragment : Fragment() {
    private var _binding: FragmentEmailForChangePasswordBinding? = null
    private val binding get() = _binding!!
    private val verificationViewModel: VerificationViewModel by viewModels()
    @Inject
    lateinit var preferences: Preferences
    private lateinit var email: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEmailForChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeValidationResponse()
        validateFields()
        with(binding) {
            fragmentNextBtn.setOnClickListener {
                hideKeyBoard(requireContext(), it)
                val userEmail = fragmentEnterEmailForPasswordChangeEt.text.toString()
                email = userEmail
                verificationViewModel.validateUser(userEmail)
            }
        }
    }

    private fun validateFields() {
        val fieldTypesToValidate = listOf(
            FieldValidationTracker.FieldType.EMAIL
        )
        FieldValidationTracker.populateFieldTypeMap(fieldTypesToValidate)

        binding.apply {
            fragmentEnterEmailForPasswordChangeLayout.validateField(
                getString(R.string.enter_valid_email_str),
                FieldValidationTracker.FieldType.EMAIL
            ) { input ->
                FieldValidations.verifyEmail(input)
            }
            fragmentNextBtn.observeFieldsValidationToEnableButton(
                requireContext(),
                viewLifecycleOwner
            )
        }
    }

    private fun observeValidationResponse() {
        lifecycleScope.launch {
            verificationViewModel.validationResponse.collect {
                when (it) {
                    is Resource.Loading -> {
                        binding.loader.showView()
                    }
                    is Resource.Success -> {
                        saveEmail(email)
                        if (it.value.success)
                            binding.loader.hideView()
                        showShortSnackBar(it.value.message)
                        val responseEmail = binding.fragmentEnterEmailForPasswordChangeEt.text.toString()
                        val action =
                            EmailForChangePasswordFragmentDirections.actionEmailForChangePasswordFragmentToSmsVerificationFragment(
                                responseEmail, "forgotPassword"
                            )
                        findNavController().navigate(action)
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
    private fun saveEmail(email: String) = preferences.saveEmail(email)
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}