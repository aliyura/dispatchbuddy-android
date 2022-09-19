package com.example.dispatchbuddy.presentation.ui.user_dashboard.view

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.Constants.PICKUP_REQUEST_CODE
import com.example.dispatchbuddy.common.Constants.DELIVERY_REQUEST_CODE
import com.example.dispatchbuddy.common.Constants.GOOGLE_PLACES_API_KEY
import com.example.dispatchbuddy.common.ViewExtensions.showShortSnackBar
import com.example.dispatchbuddy.common.preferences.Preferences
import com.example.dispatchbuddy.common.validation.FieldValidationTracker
import com.example.dispatchbuddy.common.validation.FieldValidations
import com.example.dispatchbuddy.common.validation.observeFieldsValidationToEnableButton
import com.example.dispatchbuddy.common.validation.validateField
import com.example.dispatchbuddy.databinding.FragmentHomeBinding
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var preferences: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.deliveryDestinationEt.setOnClickListener {
            onSearchCalled(DELIVERY_REQUEST_CODE)
        }
        binding.pickupLocationEt.setOnClickListener {
            onSearchCalled(PICKUP_REQUEST_CODE)
        }

        validateFields()
        binding.findRidersButton.setOnClickListener {
            getUserInputs()
            findNavController().navigate(R.id.ridersListFragment)
        }

       // Initialize places sdk
        Places.initialize(requireContext(), GOOGLE_PLACES_API_KEY)
    }

    private fun onSearchCalled(requestCode: Int) {
        // Set the fields to specify which types of place data to return.
        val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
        val intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.OVERLAY, fields
        ).setCountry("NG")
            .build(requireContext())
        startActivityForResult(intent, requestCode)
    }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICKUP_REQUEST_CODE) {
            when (resultCode) {
                RESULT_OK -> {
                    val place = data?.let { Autocomplete.getPlaceFromIntent(it).name }
                        binding.pickupLocationEt.setText(place)
                }
                AutocompleteActivity.RESULT_ERROR -> {
                    val status = data?.let { Autocomplete.getStatusFromIntent(it).statusMessage }
                        showShortSnackBar("$status")
                }
                RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
        }
         //delivery
         if (requestCode == DELIVERY_REQUEST_CODE) {
             when (resultCode) {
                 RESULT_OK -> {
                     val place = data?.let { Autocomplete.getPlaceFromIntent(it).name }
                     binding.deliveryDestinationEt.setText(place)
                 }
                 AutocompleteActivity.RESULT_ERROR -> {
                     val status: String? = data?.let { Autocomplete.getStatusFromIntent(it).statusMessage }
                     showShortSnackBar("$status")
                 }
                 RESULT_CANCELED -> {
                     // The user canceled the operation.
                 }
             }
         }
     }

    private fun getUserInputs(){
        val pickup = binding.pickupLocationEt.text.toString()
        val destination = binding.deliveryDestinationEt.text.toString()
        savePickAndDestinationLocation(pickup = pickup, destination = destination)
    }

    private fun savePickAndDestinationLocation(pickup: String, destination: String) {
        savePickup(pickup)
        saveDestination(destination)
    }

    private fun validateFields() {
        val fieldTypesToValidate = listOf(
            FieldValidationTracker.FieldType.COUNTRY,
            FieldValidationTracker.FieldType.CITY
        )
        FieldValidationTracker.populateFieldTypeMap(fieldTypesToValidate)
        binding.apply {
            pickupLayout.validateField(
                getString(R.string.enter_valid_pickup_str),
                FieldValidationTracker.FieldType.COUNTRY
            ){ input ->
                FieldValidations.verifyCountry(input)
            }
            deliveryDestinationLayout.validateField(
                getString(R.string.enter_valid_destination_str),
                FieldValidationTracker.FieldType.CITY
            ){ input ->
                FieldValidations.verifyCity(input)
            }
            findRidersButton.observeFieldsValidationToEnableButton(
                requireContext(),
                viewLifecycleOwner
            )
        }
    }

    private fun savePickup(pickup: String) = preferences.savePickUp(pickup)
    private fun saveDestination(destination: String) = preferences.saveDestination(destination)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}