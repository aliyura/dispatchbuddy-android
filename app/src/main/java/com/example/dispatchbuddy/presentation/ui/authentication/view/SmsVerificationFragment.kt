package com.example.dispatchbuddy.presentation.ui.authentication.view

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.annotation.Nullable
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.text.underline
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.ViewExtensions.hideKeyBoard
import com.example.dispatchbuddy.common.ViewExtensions.hideView
import com.example.dispatchbuddy.common.ViewExtensions.showShortSnackBar
import com.example.dispatchbuddy.common.ViewExtensions.showView
import com.example.dispatchbuddy.common.preferences.Preferences
import com.example.dispatchbuddy.data.remote.dto.models.VerifyUser
import com.example.dispatchbuddy.databinding.FragmentSmsVerificationBinding
import com.example.dispatchbuddy.presentation.ui.authentication.viewmodel.VerificationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class SmsVerificationFragment : Fragment() {
    private var _binding: FragmentSmsVerificationBinding? = null
    private val binding get() = _binding!!
    val args: SmsVerificationFragmentArgs by navArgs()
    private val verificationViewModel: VerificationViewModel by viewModels()
    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                handleBackNavigation()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSmsVerificationBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeVerificationResponse()
        observeValidationResponse()

        binding.apply {
            val resendCode = buildSpannedString {
                underline {
                    color(ResourcesCompat.getColor(resources, R.color.grey, null)) {
                        append(getString(R.string.resend_code))
                    }
                }
            }
            resendCodeTv.text = resendCode
            resendCodeTv.setOnClickListener {
                verificationViewModel.validateUser(preferences.getEmail())
            }
            fragmentRegisterBackArrowIv.setOnClickListener {
                handleBackNavigation()
            }
            verificationMessage.text = getString(R.string.a_code_has_been_sent_to_via_email, preferences.getEmail())

            configOtpEditText(firstEt, secondEt, thirdEt, fourthEt, fifthEt, sixthEt)
        }
    }
    private fun handleBackNavigation(){
        if (args.fragment == "registration"){
            findNavController().navigate(R.id.action_smsVerificationFragment_to_loginFragment)
        }else{
            findNavController().navigate(R.id.action_smsVerificationFragment_to_emailForChangePasswordFragment)
        }
    }

    private fun configOtpEditText(vararg etList: EditText) {
        val afterTextChanged = { index: Int, e: Editable? ->
            val view = etList[index]
            val text = e.toString()

            when (view.id) {
                // first text changed
                etList[0].id -> {
                    if (text.isNotEmpty()) etList[index + 1].requestFocus()
                }

                // las text changed
                etList[etList.size - 1].id -> {
                    if (text.isEmpty()) etList[index - 1].requestFocus()
                    else {
                        val otp =
                            etList[0].text.toString() + etList[1].text.toString() + etList[2].text.toString() +
                                    etList[3].text.toString() + etList[4].text.toString() + etList[5].text.toString()
                        Log.d("CHECKING", otp)
                        hideKeyBoard(requireContext(), binding.root)
                        verificationViewModel.verifyUser(
                            VerifyUser(
                                username = preferences.getEmail(),
                                otp = otp
                            )
                        )
                    }
                }

                // middle text changes
                else -> {
                    if (text.isNotEmpty()) etList[index + 1].requestFocus()
                    else etList[index - 1].requestFocus()
                }
            }
            false
        }
        etList.forEachIndexed { index, editText ->
            editText.doAfterTextChanged { afterTextChanged(index, it) }
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
                        binding.loader.hideView()
                        showShortSnackBar(it.value.message)
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

    private fun observeVerificationResponse() {
        lifecycleScope.launch {
            verificationViewModel.verificationStateFlow.collect {
                when (it) {
                    is Resource.Loading -> {
                        binding.loader.showView()
                    }
                    is Resource.Success -> {
                        binding.loader.hideView()
                        if (it.value.success) {
                            showShortSnackBar(it.value.message)
                            if (args.fragment == "registration")
                            findNavController().navigate(R.id.action_smsVerificationFragment_to_loginFragment)
                        else findNavController().navigate(R.id.action_smsVerificationFragment_to_changePasswordFragment)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}