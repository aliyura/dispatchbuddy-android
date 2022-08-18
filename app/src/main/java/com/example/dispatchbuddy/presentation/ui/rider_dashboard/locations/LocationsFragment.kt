package com.example.dispatchbuddy.presentation.ui.rider_dashboard.locations

import android.Manifest
import android.annotation.SuppressLint
import android.content.*
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.Constants
import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.ViewExtensions.hideView
import com.example.dispatchbuddy.common.ViewExtensions.showShortSnackBar
import com.example.dispatchbuddy.common.ViewExtensions.showShortToast
import com.example.dispatchbuddy.common.ViewExtensions.showView
import com.example.dispatchbuddy.common.locationResultList
import com.example.dispatchbuddy.common.preferences.Preferences
import com.example.dispatchbuddy.common.validation.FieldValidationTracker
import com.example.dispatchbuddy.data.remote.dto.models.Locations
import com.example.dispatchbuddy.databinding.FragmentLocationsBinding
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LocationsFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var locationResultAdapter: LocationResultAdapter
    lateinit var bottomSheetView: View
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var googleMap: GoogleMap
    var locationList = mutableSetOf<String>()
    private val locationsViewModel : LocationsViewModel by viewModels()
    @Inject
    lateinit var preferences: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLocationsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        MapsInitializer.initialize(requireContext())

        locationResultAdapter = LocationResultAdapter {
            locationList.add(it.cityName)
        }

        bottomSheetView = view.findViewById(R.id.bottom_sheet)
        setUpBottomSheetRecyclerView()
        observeCoveredLocationsResponse()

        val save : TextView= bottomSheetView.findViewById(R.id.fragment_save_btn)
        save.setOnClickListener {
            if(!locationList.isNullOrEmpty())
            locationsViewModel.addCoveredLocations(Locations( locationList.toList()), "Bearer ${preferences.getToken()}")
            else showShortSnackBar("Select location")
        }

        /* register broadcast receivers */
        val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        filter.addAction(Intent.ACTION_PROVIDER_CHANGED)
        requireContext().registerReceiver(broadcastReceiver, filter)
    }

        private fun setUpBottomSheetRecyclerView() {
        val locationResultRV: RecyclerView? = bottomSheetView.findViewById(R.id.location_result_rv)
        locationResultRV?.adapter = locationResultAdapter
        locationResultAdapter.submitList(locationResultList)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        getLocationUpdates()
    }

    override fun onResume() {
        super.onResume()
        initiateMapLunch()
    }
    @SuppressLint("MissingPermission")
    private fun getLocationUpdates() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        locationRequest = LocationRequest.create()
        locationRequest.interval = 20000
        locationRequest.fastestInterval = 10000
        locationRequest.priority = Priority.PRIORITY_HIGH_ACCURACY

        locationCallback = object : LocationCallback() {

            override fun onLocationResult(locationResult: LocationResult) {
                if (locationResult.locations.isNotEmpty()) {
                    val location = locationResult.lastLocation

                    //save to backend
                    val locationLogging = LocationModel(location?.latitude, location?.longitude)

                    googleMap.clear()
                    googleMap.isMyLocationEnabled = true
                    //get the user location, add a marker then zoom the map to city level
                    val latLng = location?.latitude?.let { LatLng(it, location.longitude) }
                    latLng?.let { CameraUpdateFactory.newLatLngZoom(it, 15f) }
                        ?.let { googleMap.animateCamera(it) }
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

    }

    /* Check for user permission to access location*/
    private fun checkPermission(): Boolean {
        return (
                ContextCompat.checkSelfPermission(
                    requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(
                            requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                )

    }

    /* requestPermission for user permission to read external storage*/
    private fun requestPermission(permission: String, name: String, requestCode: Int) {
        when {
            shouldShowRequestPermissionRationale(permission) -> showDialog(
                permission,
                name,
                requestCode
            )
            else ->
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    Constants.PERMISSION_ID
                )
        }
    }

    /* On request permission result grant user permission or show a permission denied message */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            Constants.PERMISSION_ID -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED
                ) {
                    showShortToast(getString(R.string.Permission_granted))
                } else {
                    showShortToast(getString(R.string.Permission_denied))
                }
                return
            }
            else -> {}
        }
    }

    /* build the permission and show the permission dialog*/
    private fun showDialog(permission: String, name: String, requestCode: Int) {
        val builder = AlertDialog.Builder(requireActivity())

        builder.apply {
            setMessage("Permission to access your $name is required to use this app")
            setTitle(getString(R.string.Permission_required))
            setPositiveButton(getString(R.string.ok)) { _, _ ->
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(permission),
                    requestCode
                )
            }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    //Ask for GPS Location and get current location
    private fun buildAlertMessageNoGps() {
        val locationRequest: LocationRequest = LocationRequest.create()
        locationRequest.priority = Priority.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 30 * 1000
        locationRequest.fastestInterval = 5 * 1000

        val builder: LocationSettingsRequest.Builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        builder.setAlwaysShow(true) // this is the key ingredient
        val result: Task<LocationSettingsResponse> =
            LocationServices.getSettingsClient(requireContext())
                .checkLocationSettings(builder.build())
        result.addOnCompleteListener { task ->
            try {
                val response: LocationSettingsResponse = task.getResult(ApiException::class.java)
                /**
                 * All location settings are satisfied. The client can initialize location requests here.
                 */
            } catch (exception: ApiException) {
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                        // Location settings are not satisfied. But could be fixed by showing the user a dialog.
                        try {
                            val resolvable = exception as ResolvableApiException
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            resolvable.startResolutionForResult(
                                requireActivity(),
                                Constants.REQUEST_CHECK_SETTINGS
                            )
                        } catch (e: IntentSender.SendIntentException) {
                            // Ignore the error.
                        } catch (e: ClassCastException) {
                            // Ignore, should be an impossible error.
                        }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {}
                }
            }
        }
    }

    //    /* set broadcast receiver go detect GPS changes */
    var broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (LocationManager.PROVIDERS_CHANGED_ACTION == intent.action) {
                val locationManager =
                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                if (isGpsEnabled) {
                    // Handle Location turned ON
                    /**
                     * Beware causing crash in other fragments, to be fixed
                     **/
//                    showShortSnackBar("Location Enabled")
                } else {
                    /**
                     * Beware causing crash, to be fixed
                     **/
//                    showShortSnackBar("Location Disabled")
                    // Handle Location turned OFF
                }
            }
        }
    }

    private fun isLocationEnabled(): Boolean {

        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        return locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true ||
                locationManager?.isProviderEnabled(LocationManager.NETWORK_PROVIDER) == true
    }

    // logic to lunch map if all requirements are met
    private fun initiateMapLunch() {
        if (checkPermission()) {
            if (isLocationEnabled()) {

                getLocationUpdates()
            } else {
                buildAlertMessageNoGps()
            }
        } else {
            requestPermission(
                Manifest.permission.ACCESS_FINE_LOCATION,
                "Access Location",
                Constants.PERMISSION_ID
            )

        }
    }

    private fun observeCoveredLocationsResponse() {
        val loader : ProgressBar =
            bottomSheetView.findViewById(R.id.loader)
        lifecycleScope.launch {
            locationsViewModel.locationsCoveredResponse.collect{
                when(it) {
                    is Resource.Loading -> {
                        loader.showView()
                    }

                    is Resource.Success -> {
                        loader.hideView()
                        showShortSnackBar(" Locations Saved")
                        it.value.payload.coveredLocations
                    }

                    is Resource.Error -> {
                        loader.hideView()
                        showShortSnackBar(it.error)
                    }
                    else -> {}
                }
            }
        }
    }

}