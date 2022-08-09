package com.example.dispatchbuddy.presentation.ui.user_dashboard

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.ViewExtensions.hideView
import com.example.dispatchbuddy.common.ViewExtensions.showShortSnackBar
import com.example.dispatchbuddy.common.ViewExtensions.showView
import com.example.dispatchbuddy.common.preferences.Preferences
import com.example.dispatchbuddy.data.remote.dto.models.UserContactDetails
import com.example.dispatchbuddy.data.remote.dto.models.riderRequestModel.RequestRiderContent
import com.example.dispatchbuddy.databinding.FragmentRidersListBinding
import com.example.dispatchbuddy.presentation.ui.user_dashboard.adapter.RiderListAdapter
import com.example.dispatchbuddy.presentation.ui.user_dashboard.viewmodel.UserViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class RidersListFragment : Fragment(){
    private var _binding: FragmentRidersListBinding? = null
    private val binding get() = _binding!!
    private lateinit var riderListAdapter : RiderListAdapter
    private val userViewModel: UserViewModel by viewModels()
    @Inject
    lateinit var preferences: Preferences
    private var riderNames: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRidersListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            observeRequestRiderResponse()
            getAllRiders()
    }
    private fun initRecyclerView(riderProfile: List<RequestRiderContent>) {
        riderListAdapter = RiderListAdapter { rider ->
            riderNames = rider.name
            showDialogBottomView()
        }
             binding.ridersListRv.adapter = riderListAdapter
            riderListAdapter.submitList(riderProfile)
    }
    private fun showDialogBottomView(){
        val dialog = BottomSheetDialog(requireContext())
        val dialogView = View.inflate(context,R.layout.fragment_contact_user_bootom_sheet_dialog, null)
        val name: TextInputEditText? = dialogView.findViewById(R.id.dialog_fullName_edt)
        val email: TextInputEditText? = dialogView.findViewById(R.id.dialog_email_edt)
        val phoneNumber: TextInputEditText? = dialogView.findViewById(R.id.dialog_phoneNumber_edt)
        val itemName: TextInputEditText? = dialogView.findViewById(R.id.dialog_itemName_edt)
        val itemWeight: TextInputEditText? = dialogView.findViewById(R.id.dialog_itemWeight_edt)
        val contactRiderBtn: MaterialButton = dialogView.findViewById(R.id.dialog_contact_riders_button)
        val closeBtn: View = dialogView.findViewById(R.id.close_btn)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(dialogView)
        dialog.show()
        contactRiderBtn.setOnClickListener() {
            val userContactDetails = UserContactDetails(
                name?.text.toString(),
                email?.text.toString(),
                phoneNumber?.text.toString(),
                itemName?.text.toString(),
                itemWeight?.text.toString(),
                preferences.getPickUp(),
                preferences.getDestination()
                )
            Log.d("userContactDetails", "userContactDetails: $userContactDetails")
            successDialog(riderName = riderNames)
            dialog.dismiss()
        }
        closeBtn.setOnClickListener { dialog.dismiss() }
    }
    private fun successDialog(riderName: String?){
        val dialog = Dialog(requireContext())
        val dialogSuccessView = View.inflate(context,R.layout.custom_successful_dialog_layout, null)
        val lWindowParams = WindowManager.LayoutParams()
        lWindowParams.width = WindowManager.LayoutParams.MATCH_PARENT
        lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.show()
        dialog.window?.attributes = lWindowParams
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(dialogSuccessView)
        val message: TextView = dialogSuccessView.findViewById(R.id.success_message_tv)
        val btnCloseDialog: ImageView = dialogSuccessView.findViewById(R.id.close_icon)
        message.text = "You’ve successfully contacted $riderName. $riderName will be in your location soon."
        btnCloseDialog.setOnClickListener { dialog.dismiss() }
    }

    private fun observeRequestRiderResponse(){
        lifecycleScope.launch {
            userViewModel.requestARiderResponse.collect{response ->
                when(response){
                    is Resource.Loading ->{
                        binding.riderListProgressBar.showView()
                    }
                    is Resource.Success -> {
                        binding.riderListProgressBar.hideView()
                        if (response.value.payload == null){
                            binding.ridersListRv.hideView()
                            binding.emptyRiderListState.showView()
                        }else{
                            initRecyclerView(response.value.payload.requestRiderContent)
                        }
                    }
                    is Resource.Error ->{
                        binding.riderListProgressBar.hideView()
                        showShortSnackBar(response.error)
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getAllRiders(){
        userViewModel.requestARider(0,preferences.getPickUp(), preferences.getDestination())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}