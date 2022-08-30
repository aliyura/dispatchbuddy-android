package com.example.dispatchbuddy.presentation.ui.rider_dashboard

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.Constants
import com.example.dispatchbuddy.common.preferences.Preferences
import com.example.dispatchbuddy.databinding.FragmentDeliveriesBinding
import com.example.dispatchbuddy.presentation.ui.rider_dashboard.adapter.*
import com.example.dispatchbuddy.presentation.ui.rider_dashboard.viewmodel.RiderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DeliveriesFragment : Fragment() {
    private var _binding: FragmentDeliveriesBinding? = null
    private val binding get() = _binding!!
    private val riderViewModel: RiderViewModel by viewModels()
    private lateinit var deliveriesPaginationAdapter: DeliveriesPaginationAdapter
    @Inject
    lateinit var preferences: Preferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDeliveriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvInit()
        paginationDeliveriesObserver()
        pagingSetUpObservers()
        pagingGetRequests()
        btnClicks()
    }

    private fun btnClicks(){
        binding.fragmentDeliveriesBackArrowIv.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun pagingSetUpObservers() {
        lifecycleScope.launch {
            riderViewModel.pagingRequestResponse.collect{ pagingResponse ->
                if (pagingResponse == null) {
                    binding.emptyRequestListState.isVisible = true
                }else{
                    deliveriesPaginationAdapter.submitData(pagingResponse)
                    deliveriesPaginationAdapter.notifyDataSetChanged()
                }
            }
        }
    }
    private fun pagingGetRequests(){
        riderViewModel.deliveryRequest(Constants.STARTING_PAGE,"Bearer ${preferences.getToken()}")
    }
    private fun rvInit(){
        val recyclerView = binding.fragmentDeliveriesItemRv
        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            deliveriesPaginationAdapter = DeliveriesPaginationAdapter{
                showRatingDialog()
            }
            adapter = deliveriesPaginationAdapter
            deliveriesPaginationAdapter.notifyDataSetChanged()
        }
    }
    private fun paginationDeliveriesObserver(){
        deliveriesPaginationAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading){
                if (deliveriesPaginationAdapter.snapshot().isEmpty()){
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
                    if (deliveriesPaginationAdapter.snapshot().isEmpty()){
                        binding.emptyRequestListState.isVisible = true
                    }
                }
            }
        }
    }

    private fun showRatingDialog(){
        val dialog = Dialog(requireContext())
        val ratingsDialog = View.inflate(context, R.layout.ratings_dialog_layout, null)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val width = (resources.displayMetrics.widthPixels * 0.80).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.35).toInt()
        dialog.window?.setLayout(width, height)
        dialog.show()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(ratingsDialog)
        val closeDialogBtn: View = ratingsDialog.findViewById(R.id.close)
        val ratingBar: RatingBar = ratingsDialog.findViewById(R.id.ratingBar)
        closeDialogBtn.setOnClickListener { dialog.dismiss() }
        ratingBar.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { _, _, _ ->
                lifecycleScope.launch {
                    delay(1000L)
                    showRatingSuccess()
                    dialog.dismiss()
                }
            }
    }
    private fun showRatingSuccess(){
        val dialog = Dialog(requireContext())
        val ratingsDialog = View.inflate(context, R.layout.ratings_success_dialog_layout, null)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val width = (resources.displayMetrics.widthPixels * 0.80).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.35).toInt()
        dialog.window?.setLayout(width, height)
        dialog.show()
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setContentView(ratingsDialog)
        lifecycleScope.launch {
            delay(2000L)
            dialog.dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}