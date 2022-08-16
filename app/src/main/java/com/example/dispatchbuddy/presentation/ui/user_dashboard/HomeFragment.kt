package com.example.dispatchbuddy.presentation.ui.user_dashboard

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dispatchbuddy.BuildConfig
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.Constants.AUTOCOMPLETE_REQUEST_CODE
import com.example.dispatchbuddy.common.ViewExtensions.showShortSnackBar
import com.example.dispatchbuddy.common.ViewExtensions.showShortToast
import com.example.dispatchbuddy.common.preferences.Preferences
import com.example.dispatchbuddy.common.validation.FieldValidationTracker
import com.example.dispatchbuddy.common.validation.FieldValidations
import com.example.dispatchbuddy.common.validation.observeFieldsValidationToEnableButton
import com.example.dispatchbuddy.common.validation.validateField
import com.example.dispatchbuddy.databinding.FragmentHomeBinding
import com.google.android.gms.common.api.Status
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
    var queryText: String = "empty"
    val TAG = "HomeFragment"

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


        Log.i(TAG, "Query text $queryText")

        binding.deliveryDestinationEt.setOnClickListener {
            showShortToast("clicked")
            onSearchCalled()
        }
        binding.pickupLocationEt.setOnClickListener {
            showShortToast("clicked")
            onSearchCalled()
        }

        validateFields()
        binding.findRidersButton.setOnClickListener {
            getUserInputs()
            findNavController().navigate(R.id.ridersListFragment)
        }

       // Initialize places sdk
        Places.initialize(requireContext(), BuildConfig.GOOGLE_PLACES_API_KEY)
    }

    private fun onSearchCalled() {
        // Set the fields to specify which types of place data to return.
        val fields = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
        // Start the autocomplete intent.
        val intent = Autocomplete.IntentBuilder(
            AutocompleteActivityMode.OVERLAY, fields
        ).setCountry("NG") //NIGERIA
            .build(requireContext())
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                RESULT_OK -> {
                    val place = Autocomplete.getPlaceFromIntent(data)
                    Log.i(TAG, "Place: " + place.name + ", " + place.id + ", " + place.address)
                    binding.deliveryDestinationEt.setText(place.name)
                    binding.pickupLocationEt.setText(place.name)
                    showShortSnackBar(place.name)

                }
                AutocompleteActivity.RESULT_ERROR -> {
                    val status: Status = Autocomplete.getStatusFromIntent(data)
                    showShortSnackBar("${status.statusMessage}")
                    status.statusMessage?.let { Log.i(TAG, it) }
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