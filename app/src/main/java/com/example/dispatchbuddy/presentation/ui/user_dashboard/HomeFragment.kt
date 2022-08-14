package com.example.dispatchbuddy.presentation.ui.user_dashboard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dispatchbuddy.BuildConfig
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.ViewExtensions.showView
import com.example.dispatchbuddy.common.preferences.Preferences
import com.example.dispatchbuddy.common.validation.FieldValidationTracker
import com.example.dispatchbuddy.common.validation.FieldValidations
import com.example.dispatchbuddy.common.validation.observeFieldsValidationToEnableButton
import com.example.dispatchbuddy.common.validation.validateField
import com.example.dispatchbuddy.databinding.FragmentHomeBinding
import com.example.dispatchbuddy.presentation.ui.user_dashboard.adapter.PlacesAutoCompleteAdapter
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var placesAutoCompleteAdapter : PlacesAutoCompleteAdapter
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

        placesAutoCompleteAdapter = PlacesAutoCompleteAdapter {
            Log.i(TAG, "Query text item clicked ${it.getFullText(null)}")
            binding.pickupLocationEt.text?.setSpan(it.getFullText(null), 0, 0, 0)
        }

        validateFields()
        binding.findRidersButton.setOnClickListener {
            getUserInputs()
            findNavController().navigate(R.id.ridersListFragment)
        }

        // Initialize places sdk
        Places.initialize(requireContext(), BuildConfig.GOOGLE_PLACES_API_KEY)
        binding.pickupLocationEt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val recyclerView =  binding.placesAutocompleteContentRv
                if (p0.toString().trim().isNotEmpty() && p0.toString().length != 1){
                    if (recyclerView.visibility == View.GONE) {
                        recyclerView.visibility = View.VISIBLE}
                } else {
                    if (recyclerView.visibility == View.VISIBLE) {
                        recyclerView.visibility = View.GONE}
                }

                binding.placesAutocompleteContentRv.showView()
                autoCompleteSearch(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
    }

    private fun autoCompleteSearch(query : String) {
        Log.i(TAG, "IM INNN")
        Log.i(TAG, query)
        // create a new places client instance
        val placesClient: PlacesClient = Places.createClient(requireContext())
        val token = AutocompleteSessionToken.newInstance()
        // Create a RectangularBounds object.
        val bounds = RectangularBounds.newInstance(
            LatLng(-33.880490, 151.184363),
            LatLng(-33.858754, 151.229596)
        )

        Log.i(TAG, "$token")
        // Use the builder to create a FindAutocompletePredictionsRequest.
        val request =
            FindAutocompletePredictionsRequest.builder()
                // Call either setLocationBias() OR setLocationRestriction().
                .setLocationBias(bounds)
                //.setLocationRestriction(bounds)
                .setOrigin(LatLng(-33.8749937, 151.2041382))
                .setCountries("ng")
                .setTypeFilter(TypeFilter.CITIES)
                .setSessionToken(token)
                .setQuery(query)
                .build()
        Log.i(TAG, query)
        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                Log.i(TAG, "Success")
                Log.i(TAG, response.toString())
                for (prediction in response.autocompletePredictions) {
                    Log.i(TAG, prediction.placeId)
                    Log.i(TAG, prediction.getPrimaryText(null).toString())

                }
                Log.i(TAG, "getting response : ${response.autocompletePredictions}")
                setUpRecyclerView(response.autocompletePredictions)
            }.addOnFailureListener { exception: Exception? ->
                if (exception is ApiException) {
                    Log.e(TAG, "Place not found: " + exception.statusCode)
                }
            }
    }

    private fun setUpRecyclerView(placesList : List<AutocompletePrediction>) {
        binding.placesAutocompleteContentRv.adapter = placesAutoCompleteAdapter
        placesAutoCompleteAdapter.submitList(placesList)
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