package com.example.dispatchbuddy.presentation.ui.rider_dashboard

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.*
import com.example.dispatchbuddy.common.Constants.STARTING_PAGE
import com.example.dispatchbuddy.common.ViewExtensions.hideView
import com.example.dispatchbuddy.common.ViewExtensions.showShortSnackBar
import com.example.dispatchbuddy.common.ViewExtensions.showView
import com.example.dispatchbuddy.common.preferences.Preferences
import com.example.dispatchbuddy.common.validation.FieldValidationTracker
import com.example.dispatchbuddy.common.validation.FieldValidations
import com.example.dispatchbuddy.common.validation.observeFieldsValidationToEnableButton
import com.example.dispatchbuddy.common.validation.validateField
import com.example.dispatchbuddy.data.remote.dto.RiderResponse
import com.example.dispatchbuddy.data.remote.dto.RiderSectionResponse
import com.example.dispatchbuddy.data.remote.dto.models.userRequestStatusModel.RejectUserRideModel
import com.example.dispatchbuddy.databinding.FragmentRequestBinding
import com.example.dispatchbuddy.presentation.ui.rider_dashboard.adapter.PaginationAdapter
import com.example.dispatchbuddy.presentation.ui.rider_dashboard.adapter.RequestAdapter
import com.example.dispatchbuddy.presentation.ui.rider_dashboard.viewmodel.RiderViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RequestFragment : Fragment() {
    private var _binding: FragmentRequestBinding? = null
    private val binding get() = _binding!!
    private lateinit var requestAdapter: RequestAdapter
    private val responseList: ArrayList<RiderResponse> = ArrayList()
    private val sectionResponse: ArrayList<RiderSectionResponse> = ArrayList()
    private lateinit var pagingAdapter: PaginationAdapter
    private lateinit var requestUserId: String
    private lateinit var requestUserStatus: String
    private val riderViewModel: RiderViewModel by viewModels()
    private lateinit var successDialog: Dialog
    @Inject
    lateinit var preferences: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
            paginationStateObserver()
            pagingSetUpObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerRejectUserRequestResponse()
        observerAcceptUserRequestResponse()
        observerCloseUserRequestResponse()
        initRV()
        paginationStateObserver()
        pagingSetUpObservers()
        pagingGetRequests()
        initRefreshListener()
        btnClicks()
        updateUIonRiderAction()
    }

    private fun populateData(list: List<RiderResponse>){
        try {
            responseList.clear()
            sectionResponse.clear()
            responseList.addAll(list)
            val dateList = responseList.groupBy { it.date.subSequence(0,10) }
            Log.d("dateList", "populateData: $dateList")
            val distinctDate = dateList.keys.distinct().toList()
            Log.d("distinctDate", "populateData: $distinctDate")
            val dateValues = dateList.values
            Log.d("dateValues", "populateData: $dateValues")
            for (i in dateValues.indices){
                sectionResponse.add(
                    RiderSectionResponse(
                        distinctDate,
                        dateValues.elementAt(i)
                    )
                )
            }
            requestAdapter.notifyDataSetChanged()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
    private fun initRecyclerview(){
        val recyclerView = binding.fragmentRequestRv
        recyclerView.apply {
            val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerView.layoutManager = layoutManager
            requestAdapter = RequestAdapter(sectionResponse, requireContext())
            recyclerView.adapter = requestAdapter
            requestAdapter.notifyDataSetChanged()
        }
    }

    private fun btnClicks(){
        with(binding){
            fragmentRequestBackArrowIv.setOnClickListener { findNavController().popBackStack() }
        }
    }

    private fun pagingSetUpObservers() {
        lifecycleScope.launch {
            riderViewModel.pagingRequestResponse.collect{ pagingResponse ->
                if (pagingResponse == null) {
                    binding.emptyRequestListState.isVisible = true
                }else{
                    pagingAdapter.submitData(pagingResponse)
                    pagingAdapter.notifyDataSetChanged()
                }
            }
        }
    }
    private fun pagingGetRequests(){
        riderViewModel.pagingRequest(STARTING_PAGE,"Bearer ${preferences.getToken()}")
    }
    private fun initRV(){
        val recyclerView = binding.fragmentRequestChildRv
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            pagingAdapter = PaginationAdapter{
                requestUserId = it.id
                requestUserStatus = it.status
                incomingRequestDialog()
            }
            adapter = pagingAdapter
        }
    }
    private fun paginationStateObserver(){
        pagingAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading){
                if (pagingAdapter.snapshot().isEmpty()){
                    binding.riderListRequestProgressBar.isVisible = true
                }
                binding.emptyRequestListState.isVisible = false
            }else {
                binding.riderListRequestProgressBar.isVisible = false
                val error = when  {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                error?.let {
                    if (pagingAdapter.snapshot().isEmpty()){
                        binding.emptyRequestListState.isVisible = true
                    }
                }
            }
        }
    }

    private fun incomingRequestDialog(){
        val dialog = Dialog(requireContext())
        val dialogSuccessView = View.inflate(context, R.layout.incoming_request_dialog_layout, null)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val width = (resources.displayMetrics.widthPixels * 0.80).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.35).toInt()
        dialog.window?.setLayout(width, height)
        dialog.show()
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(dialogSuccessView)
        val acceptButton: ImageView = dialogSuccessView.findViewById(R.id.accept_button)
        val rejectButton: ImageView = dialogSuccessView.findViewById(R.id.reject_button)
        val closeButton: MaterialButton = dialogSuccessView.findViewById(R.id.completed_btn)

        closeButton.setOnClickListener {
            when (requestUserStatus) {
                "AC" -> {
                    closeUserRequest()
                }
                "CO" -> {
                    showShortSnackBar(getString(R.string.ride_closed))
                }
                "RJ" -> {
                    showShortSnackBar(getString(R.string.ride_not_accepted))
                }
                "PC" -> {
                    showShortSnackBar(getString(R.string.ride_not_accepted))
                }
            }
            dialog.dismiss()
        }
        acceptButton.setOnClickListener {
            if (requestUserStatus != "CO") acceptUserRequest() else showShortSnackBar(getString(R.string.ride_closed))
            dialog.dismiss()
        }
        rejectButton.setOnClickListener {
            if (requestUserStatus != "CO") showRejectionReasonBottomSheet() else showShortSnackBar(getString(R.string.ride_closed))
            dialog.dismiss()
        }
    }
    private fun showRejectionReasonBottomSheet(){
        val dialog = BottomSheetDialog(requireContext())
        val dialogView = View.inflate(context,R.layout.fragment_reject_reason_bootom_sheet_dialog, null)
        val rejectionReason: TextInputEditText? = dialogView.findViewById(R.id.dialog_rejection_reason_edt)
        val saveReasonBtn: MaterialButton = dialogView.findViewById(R.id.dialog_rejection_reason_riders_button)
        val rejectionReasonLayout: TextInputLayout = dialogView.findViewById(R.id.dialog_rejection_reason_layout)

        validateFields(rejectReasonLayout = rejectionReasonLayout, saveReasonBtn)

        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(dialogView)
        dialog.show()

        saveReasonBtn.setOnClickListener {
            rejectUserRequest(requestUserId, rejectionReason?.text.toString(),"Bearer ${preferences.getToken()}")
            dialog.dismiss()
        }
    }

    private fun observerRejectUserRequestResponse(){
        lifecycleScope.launch {
            riderViewModel.rejectUserRequestResponse.collect{ response ->
                when(response){
                    is Resource.Loading ->{
                        binding.riderListRequestProgressBar.showView()
                    }
                    is Resource.Success -> {
                        pagingGetRequests()
                        updateUI()
                        successDialog.show()
                        binding.riderListRequestProgressBar.hideView()
                    }
                    is Resource.Error ->{
                        binding.riderListRequestProgressBar.hideView()
                        showShortSnackBar(response.error)
                    }
                    else -> {}
                }
            }
        }
    }
    private fun observerAcceptUserRequestResponse(){
        lifecycleScope.launch {
            riderViewModel.acceptUserRequestResponse.collect{ response ->
                when(response){
                    is Resource.Loading ->{
                        binding.riderListRequestProgressBar.showView()
                    }
                    is Resource.Success -> {
                        binding.riderListRequestProgressBar.hideView()
                        if(response.value.message != "You already have an active ride"){
                            pagingGetRequests()
                            successDialog.show()
                        }else{
                            showShortSnackBar(response.value.message)
                        }
                    }
                    is Resource.Error ->{
                        binding.riderListRequestProgressBar.hideView()
                        showShortSnackBar(response.error)
                    }
                    else -> {}
                }
            }
        }
    }
    private fun observerCloseUserRequestResponse(){
        lifecycleScope.launch {
            riderViewModel.closeUserRequestResponse.collect{ response ->
                when(response){
                    is Resource.Loading ->{
                        binding.riderListRequestProgressBar.showView()
                    }
                    is Resource.Success -> {
                        updateUI()
//                        successDialog.show()
                        binding.riderListRequestProgressBar.hideView()
                    }
                    is Resource.Error ->{
                        binding.riderListRequestProgressBar.hideView()
                        showShortSnackBar(response.error)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun acceptUserRequest(){
        riderViewModel.acceptUserRequest(requestUserId, "Bearer ${preferences.getToken()}")
    }
    private fun closeUserRequest(){
        riderViewModel.closeUserRequest(requestUserId, "Bearer ${preferences.getToken()}")
    }
    private fun rejectUserRequest(id: String, rejectReason: String, token: String) {
        riderViewModel.rejectUserRequest(RejectUserRideModel(id, rejectReason),token)
    }

    private fun initRefreshListener() {
        val swipeRefreshLayout = binding.swipeRefreshLayoutId
        swipeRefreshLayout.setOnRefreshListener {
            pagingGetRequests()
            paginationStateObserver()
            pagingAdapter.notifyDataSetChanged()
            lifecycleScope.launch {
                delay(2000L)
                if (swipeRefreshLayout.isRefreshing) {
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        }
    }
    private fun updateUI(){
        pagingGetRequests()
        paginationStateObserver()
        pagingAdapter.notifyDataSetChanged()
    }
    private fun updateUIonRiderAction(){
        successDialog = showSuccessDialog(requireContext(), resources){ updateUI()}
    }

    private fun validateFields(rejectReasonLayout: TextInputLayout, saveReason: MaterialButton) {
        val fieldTypesToValidate = listOf(FieldValidationTracker.FieldType.FULLNAME)
        FieldValidationTracker.populateFieldTypeMap(fieldTypesToValidate)
        binding.apply {
            rejectReasonLayout.validateField(
                getString(R.string.enter_valid_reject_reason_str),
                FieldValidationTracker.FieldType.FULLNAME
            ) { input ->
                FieldValidations.verifyName(input)
            }
            saveReason.observeFieldsValidationToEnableButton(
                requireContext(),
                viewLifecycleOwner
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
