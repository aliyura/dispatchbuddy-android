package com.example.dispatchbuddy.presentation.ui.profile.view

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
import com.example.dispatchbuddy.common.preferences.Preferences
import com.example.dispatchbuddy.common.validation.FieldValidationTracker
import com.example.dispatchbuddy.common.validation.FieldValidations
import com.example.dispatchbuddy.common.validation.observeFieldsValidationToEnableButton
import com.example.dispatchbuddy.common.validation.validateField
import com.example.dispatchbuddy.data.remote.dto.models.ChangePassword
import com.example.dispatchbuddy.databinding.FragmentResetPasswordBinding
import com.example.dispatchbuddy.presentation.ui.profile.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

@AndroidEntryPoint
class ResetPasswordFragment : Fragment() {
    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()
    private var emailPre: String? = null
    @Inject
    lateinit var preferences: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        validateFields()
        observeChangePasswordResponse()
        changeUserPassword()
        emailPre = preferences.getEmail()

    }

    private fun observeChangePasswordResponse(){
        lifecycleScope.launch {
            profileViewModel.resetPasswordResponse.collect{
                when(it){
                    is Resource.Loading ->{
                        binding.changePasswordProgressBar.showView()
                    }
                    is Resource.Success ->{
                        binding.changePasswordProgressBar.hideView()
                        showShortSnackBar(it.value.message)
                           findNavController().navigate(R.id.profileFragment)
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
    private fun changeUserPassword(){
        with(binding){
            fragmentResetPasswordBtn.setOnClickListener {
                val oldPassword = fragmentResetPasswordOldEdt.text.toString()
                val newPassword = fragmentResetPasswordNewEdt.text.toString()
                if (oldPassword != newPassword){
                    showShortSnackBar(getString(R.string.password_not_matched))
                }else{
                    profileViewModel.resetUserPassword(ChangePassword(username = emailPre.toString(), password = newPassword))
                }
            }
        }
    }

    private fun validateFields() {
        val fieldTypesToValidate = listOf(
            FieldValidationTracker.FieldType.PASSWORD,
            FieldValidationTracker.FieldType.PASSWORD
        )
        FieldValidationTracker.populateFieldTypeMap(fieldTypesToValidate)

        binding.apply {
            fragmentResetPasswordOldLayout.validateField(
                getString(R.string.enter_valid_password_str),
                FieldValidationTracker.FieldType.PASSWORD
            ) { input ->
                FieldValidations.verifyPassword(input)
            }
            fragmentResetPasswordNewLayout.validateField(
                getString(R.string.enter_valid_password_str),
                FieldValidationTracker.FieldType.PASSWORD
            ) { input ->
                FieldValidations.verifyPassword(input)
            }
            fragmentResetPasswordBtn.observeFieldsValidationToEnableButton(
                requireContext(),
                viewLifecycleOwner
            )
        }
    }
}