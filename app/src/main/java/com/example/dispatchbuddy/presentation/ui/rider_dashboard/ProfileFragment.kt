package com.example.dispatchbuddy.presentation.ui.rider_dashboard

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.Constants
import com.example.dispatchbuddy.common.Constants.dummyId
import com.example.dispatchbuddy.common.Constants.dummyToken
import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.ViewExtensions.hideView
import com.example.dispatchbuddy.common.ViewExtensions.showShortSnackBar
import com.example.dispatchbuddy.common.ViewExtensions.showView
import com.example.dispatchbuddy.common.getFileName
import com.example.dispatchbuddy.common.preferences.Preferences
import com.example.dispatchbuddy.databinding.FragmentProfileBinding
import com.example.dispatchbuddy.presentation.ui.rider_dashboard.viewmodel.RiderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val riderViewModel: RiderViewModel by viewModels()
    @Inject
    lateinit var preferences: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonClickListener()
        uploadImage()
        observeImageUploadResponse()
        observeGetUserResponse()
        getUserDetails()
        val id = preferences.getUserId()
        Log.d("USER_ID", "Profile: $id")
    }
    private fun buttonClickListener(){
        with(binding){
            fragmentEditProfileTv.setOnClickListener {
                findNavController().navigate(R.id.editProfileFragment)
            }
            fragmentChangePasswordProfileTv.setOnClickListener {
                val action = ProfileFragmentDirections.actionProfileFragmentToChangePasswordFragment2()
                findNavController().navigate(action)
            }
            fragmentEditProfileDeliveriesLayout.setOnClickListener {
                findNavController().navigate(R.id.deliveriesFragment)
            }
        }
    }
    private fun uploadImage(){
        binding.fragmentProfileAvatarPicker.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    //permission denied
                    val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    //show popup to request runtime permission
                    requestPermissions(permission, Constants.GALLERY_PERMISSION_CODE)
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
        startActivityForResult(intent, Constants.GALLERY)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            Constants.GALLERY_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission from popup granted
                    Toast.makeText(requireContext(),"permission granted", Toast.LENGTH_SHORT).show()
                    pickupImageFromGallery()
                } else {
                    Toast.makeText(requireContext(),"permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Constants.GALLERY) {
            val image: Uri? = data?.data
            uploadImage(image)
            binding.fragmentProfileAvatar.setImageURI(image)
        }
    }
    private fun uploadImage(selectedImage:Uri?) {
        if (selectedImage == null) {
            showShortSnackBar("Select an Image")
            return
        }
        val parcelFileDescriptor = context?.contentResolver?.openFileDescriptor(selectedImage!!, "r", null)?:  return
        val file = File(requireActivity().cacheDir, requireActivity().contentResolver.getFileName(selectedImage!!))
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream )
        riderViewModel.uploadImage(MultipartBody.Part.createFormData("dp",file.name,file.asRequestBody()),"Bearer $dummyToken" )
    }

    private fun getUserDetails(){
        riderViewModel.getUser(preferences.getUserId(), "Bearer ${preferences.getToken()}")
    }

    private fun observeImageUploadResponse(){
        lifecycleScope.launch {
            riderViewModel.imageUploadResponse.collect{ response ->
                when(response){
                    is Resource.Loading ->{
                        binding.profileProgressBar.showView()
                    }
                    is Resource.Success ->{
                        binding.profileProgressBar.hideView()
                        showShortSnackBar(response.value.message)
                    }
                    is Resource.Error ->{
                        binding.profileProgressBar.hideView()
                        showShortSnackBar(response.error)
                    }
                    else -> {}
                }
            }
        }
    }
    private fun observeGetUserResponse(){
        lifecycleScope.launch {
            riderViewModel.getUser.collect{ response ->
                when(response){
                    is Resource.Loading ->{}
                    is Resource.Success ->{
                        binding.profileProgressBar.hideView()
                        with(binding){
                            fragmentProfileNameTv.text = response.value.payload.name
                            fragmentProfileUserTypeTv.text = response.value.payload.accountType
                        }
                        val name = response.value.payload.name
                        val dob = response.value.payload.dateOfBirth
                        saveUserNameAndDateOfBirth(name = name, dob = dob)
                        val fileName = response.value.payload.dp
                        Log.d("fileName", "observeGetUserResponse: $fileName")
//                        val image = "https://lenos.s3.amazonaws.com/pictures/${fileName}"
//                        Glide.with(requireContext())
//                            .load(image)
//                            .into(binding.fragmentProfileAvatar)
                    }
                    is Resource.Error ->{
                        binding.profileProgressBar.hideView()
                        showShortSnackBar(response.error)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun saveUserNameAndDateOfBirth(name: String, dob: String) {
        saveUserName(name)
        saveDateOfBirth(dob)
    }
    private fun saveUserName(name: String) = preferences.saveUserName(name)
    private fun saveDateOfBirth(dob: String) = preferences.saveDateOfBirth(dob)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}