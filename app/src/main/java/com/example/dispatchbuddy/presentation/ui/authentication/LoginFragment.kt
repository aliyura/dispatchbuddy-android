package com.example.dispatchbuddy.presentation.ui.authentication

import android.content.Intent
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
import com.example.dispatchbuddy.common.ViewExtensions.hideView
import com.example.dispatchbuddy.common.ViewExtensions.showShortSnackBar
import com.example.dispatchbuddy.common.ViewExtensions.showView
import com.example.dispatchbuddy.common.validation.FieldValidationTracker
import com.example.dispatchbuddy.common.validation.FieldValidations
import com.example.dispatchbuddy.common.validation.observeFieldsValidationToEnableButton
import com.example.dispatchbuddy.common.validation.validateField
import com.example.dispatchbuddy.databinding.FragmentLoginBinding
import com.example.dispatchbuddy.presentation.ui.authentication.viewmodel.LoginViewModel
import com.example.dispatchbuddy.presentation.ui.rider_dashboard.RiderActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel : LoginViewModel by viewModels()

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

        observe()
        validateFields()
        with(binding) {
            fragmentLoginNoAccountTv.setOnClickListener {
                findNavController().navigate(R.id.registerFragment)
            }
            fragmentLoginLoginBtn.setOnClickListener {
                loginUser()
            }
            fragmentLoginForgotPasswordTv.setOnClickListener {
                findNavController().navigate(R.id.changePasswordFragment)
            }
        }
    }

    private fun validateFields() {
        val fieldTypesToValidate = listOf(
            FieldValidationTracker.FieldType.EMAIL,
            FieldValidationTracker.FieldType.PASSWORD
        )
        FieldValidationTracker.populateFieldTypeMap(fieldTypesToValidate)

        binding.apply {
            fragmentLoginEmailLayout.validateField(
                getString(R.string.enter_valid_email_str),
                FieldValidationTracker.FieldType.EMAIL
            ) { input ->
                FieldValidations.verifyEmail(input)
            }
            fragmentLoginPasswordLayout.validateField(
                getString(R.string.enter_valid_password_str),
                FieldValidationTracker.FieldType.PASSWORD
            ) { input ->
                FieldValidations.verifyPassword(input)
            }
            fragmentLoginLoginBtn.observeFieldsValidationToEnableButton(
                requireContext(),
                viewLifecycleOwner
            )
        }
    }

   private fun loginUser() {
        with(binding) {
            val email = fragmentLoginEmailEdt.text.toString()
            val password = fragmentLoginPasswordEdt.text.toString()

            loginViewModel.loginUser(username = email, password = password, grant_type = "password")
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            loginViewModel.loginResponse.collect{
                when(it) {
                    is Resource.Loading -> {
                        binding.loader.showView()
                    }
                    is Resource.Success -> {
                        binding.loader.hideView()
                        showShortSnackBar(it.value.message)
                        Intent(requireContext(), RiderActivity::class.java).also { intent ->
                            startActivity(intent)
                            activity?.finish()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}