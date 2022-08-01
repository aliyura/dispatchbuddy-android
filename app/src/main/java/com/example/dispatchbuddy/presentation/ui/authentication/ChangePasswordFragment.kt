package com.example.dispatchbuddy.presentation.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.ViewExtensions.hideView
import com.example.dispatchbuddy.common.ViewExtensions.showShortSnackBar
import com.example.dispatchbuddy.common.ViewExtensions.showView
import com.example.dispatchbuddy.common.handleBackPress
import com.example.dispatchbuddy.common.popBackStack
import com.example.dispatchbuddy.common.validation.*
import com.example.dispatchbuddy.common.validation.FieldValidationTracker
import com.example.dispatchbuddy.data.remote.dto.models.ChangePassword
import com.example.dispatchbuddy.databinding.FragmentChangePasswordBinding
import com.example.dispatchbuddy.presentation.ui.authentication.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChangePasswordFragment : Fragment() {
    private  var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()

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
        observeChangePasswordResponse()
        handleBackPress()
        binding.fragmentChangePasswordBtn.setOnClickListener {
            changeUserPassword()
        }
        binding.fragmentLoginBackArrowIv.setOnClickListener {
            popBackStack()
        }
    }

    private fun changeUserPassword(){
        with(binding){
            val username = fragmentLoginEmailEdt.text.toString()
            val password = fragmentLoginPasswordEdt.text.toString()
            authViewModel.changePassword(ChangePassword(username = username, password = password))
        }
    }

    private fun observeChangePasswordResponse(){
        lifecycleScope.launch {
            authViewModel.changePasswordResponse.collect{
                when(it){
                    is Resource.Loading ->{
                        binding.changePasswordProgressBar.showView()
                    }
                    is Resource.Success ->{
                        binding.changePasswordProgressBar.hideView()
                        showShortSnackBar(it.value.message)
                    }
                    is Resource.Error ->{
                        binding.changePasswordProgressBar.hideView()
                        showShortSnackBar(it.error)
                    }
                    else -> {}
                }
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