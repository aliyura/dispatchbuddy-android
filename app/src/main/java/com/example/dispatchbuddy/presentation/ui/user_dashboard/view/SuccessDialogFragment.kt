package com.example.dispatchbuddy.presentation.ui.user_dashboard.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.databinding.FragmentSuccessDialogBinding

class SuccessDialogFragment : DialogFragment() {
    private  var _binding: FragmentSuccessDialogBinding? = null
    private val binding get() = _binding!!
    val args: SuccessDialogFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSuccessDialogBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

        val message = buildSpannedString {
            append(getString(R.string.success_message_begin))
            append(" ")
            bold { color(ResourcesCompat.getColor(resources, R.color.textColor, null)) {
                append(getString(R.string.success_message_rider_name, args.riderDetails.name))
            } }
            append("\n")
            append(getString(R.string.success_message_end))
        }
        binding.successMessageTv.text = message
    }

}