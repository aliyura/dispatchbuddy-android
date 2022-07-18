package com.example.dispatchbuddy.presentation.ui.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.text.underline
import androidx.fragment.app.Fragment
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.databinding.FragmentSmsVerificationBinding

class SmsVerificationFragment : Fragment() {
    private var _binding: FragmentSmsVerificationBinding? = null
    private val binding get() = _binding!!

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

        binding.apply {
            val resendCode = buildSpannedString {
                underline {
                    color(ResourcesCompat.getColor(resources, R.color.grey, null)) {
                        append(getString(R.string.resend_code))
                    }
                }
            }
            resendCodeTv.text = resendCode
        }
    }
}