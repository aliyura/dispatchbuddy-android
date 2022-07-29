package com.example.dispatchbuddy.presentation.ui.profile

import android.os.Bundle
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.Constants.GALLERY
import com.example.dispatchbuddy.common.Constants.GALLERY_PERMISSION_CODE
import com.example.dispatchbuddy.common.validation.FieldValidationTracker
import com.example.dispatchbuddy.common.validation.FieldValidations
import com.example.dispatchbuddy.common.validation.observeFieldsValidationToEnableButton
import com.example.dispatchbuddy.common.validation.validateField
import com.example.dispatchbuddy.databinding.FragmentEditProfileBinding
import java.net.URI

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setUpDropdownMenu()
        validateFields()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragmentEditProfileSaveBtn.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }
        uploadImage()

    }
    private fun setUpDropdownMenu(){
        val genderType = resources.getStringArray(R.array.Gender)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, genderType)
        binding.selectGenderDropdown.setAdapter(arrayAdapter)
    }

    private fun uploadImage(){
        binding.fragmentProfileAvatarPicker.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    //permission denied
                    val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    //show popup to request runtime permission
                    requestPermissions(permission, GALLERY_PERMISSION_CODE)
                } else {
                    //permission already granted
                    pickupImageFromGallery()
                }
            } else {
                //system os is < Marshmallow
                pickupImageFromGallery()
            }
        }
    }

    private fun pickupImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            GALLERY_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission from popup granted
                    Toast.makeText(requireContext(),"permission granted",Toast.LENGTH_SHORT).show()
                    pickupImageFromGallery()
                } else {
                    Toast.makeText(requireContext(),"permission denied",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY) {
            val image: Uri? = data?.data
            val imageStr: String = data?.data.toString()
            Log.d("IMAGE", "--->: $imageStr ")
            binding.fragmentProfileAvatar.setImageURI(image)

        }
    }

    private fun validateFields() {
        val fieldTypesToValidate = listOf(
            FieldValidationTracker.FieldType.FULLNAME,
            FieldValidationTracker.FieldType.COUNTRY,
            FieldValidationTracker.FieldType.CITY,
            FieldValidationTracker.FieldType.GENDER
        )
        FieldValidationTracker.populateFieldTypeMap(fieldTypesToValidate)

        binding.apply {
            fragmentEditFullNameLayout.validateField(
                getString(R.string.enter_valid_name_str),
                FieldValidationTracker.FieldType.FULLNAME
            ) { input ->
                FieldValidations.verifyName(input)
            }
            fragmentEditCountryLayout.validateField(
                getString(R.string.enter_valid_country),
                FieldValidationTracker.FieldType.COUNTRY
            ) { input ->
                FieldValidations.verifyCountry(input)
            }
            fragmentEditCityLayout.validateField(
                getString(R.string.enter_valid_city),
                FieldValidationTracker.FieldType.CITY
            ) { input ->
                FieldValidations.verifyCity(input)
            }
            selectGenderDropdownLayout.validateField(
                getString(R.string.select_an_option),
                FieldValidationTracker.FieldType.GENDER
            ) { input ->
                FieldValidations.validateGender(input)
            }

            fragmentEditProfileSaveBtn.observeFieldsValidationToEnableButton(
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