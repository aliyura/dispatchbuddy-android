package com.example.dispatchbuddy.presentation.ui.rider_dashboard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.Resource
import com.example.dispatchbuddy.common.ViewExtensions.hideView
import com.example.dispatchbuddy.common.ViewExtensions.showShortSnackBar
import com.example.dispatchbuddy.common.ViewExtensions.showView
import com.example.dispatchbuddy.common.deliveriesList
import com.example.dispatchbuddy.common.preferences.Preferences
import com.example.dispatchbuddy.data.remote.dto.DeliverySectionResponse
import com.example.dispatchbuddy.data.remote.dto.models.allRequestModels.AllUserRequestResponseContent
import com.example.dispatchbuddy.databinding.FragmentDeliveriesBinding
import com.example.dispatchbuddy.presentation.ui.rider_dashboard.adapter.AllUserRequestAdapter
import com.example.dispatchbuddy.presentation.ui.rider_dashboard.adapter.ClosedRequestAdapter
import com.example.dispatchbuddy.presentation.ui.rider_dashboard.adapter.DeliveriesAdapter
import com.example.dispatchbuddy.presentation.ui.rider_dashboard.viewmodel.RiderViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DeliveriesFragment : Fragment() {
    private var _binding: FragmentDeliveriesBinding? = null
    private val binding get() = _binding!!
    private val riderViewModel: RiderViewModel by viewModels()
    private var closedListData: ArrayList<AllUserRequestResponseContent> = ArrayList()
    private var allUserRequest: ArrayList<AllUserRequestResponseContent> = ArrayList()
    private lateinit var closedRequestAdapter: ClosedRequestAdapter
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
        getAllUserRequest()
        observeGetAllRequestResponse()
    }

    private fun initRecyclerview(){
        val deliveriesItemList: ArrayList<DeliverySectionResponse> = ArrayList()
        deliveriesItemList.clear()
        deliveriesItemList.addAll(deliveriesList)
        binding.fragmentDeliveriesRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            val deliveriesAdapter = DeliveriesAdapter(deliveriesItemList)
            adapter = deliveriesAdapter
        }
    }
    private fun initializeRecyclerView(closedUserRequest: List<AllUserRequestResponseContent>){
        val recyclerView = binding.fragmentDeliveriesItemRv
        closedListData.clear()
        closedListData.addAll(closedUserRequest)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        closedRequestAdapter = ClosedRequestAdapter( closedListData){}
        recyclerView.adapter = closedRequestAdapter
        closedRequestAdapter.notifyDataSetChanged()
    }

    private fun observeGetAllRequestResponse(){
        lifecycleScope.launch {
            riderViewModel.getAllUserRequestResponse.collect{response ->
                when(response){
                    is Resource.Loading ->{
                        binding.riderListRequestProgressBar.showView()
                    }
                    is Resource.Success ->{
                        binding.riderListRequestProgressBar.hideView()
                        allUserRequest.clear()

                        if (response.value.payload == null){
                            binding.fragmentDeliveriesItemRv.hideView()
                            binding.emptyRequestListState.showView()
                        }else{
                            for (item in response.value.payload.allUserRequestResponseContent){
                                if (item.status == "CO"){
                                    allUserRequest.add(item)
                                }
                            }
                            Log.d("ClosedData", "Closed ---> $allUserRequest" )
                            initializeRecyclerView(allUserRequest)
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

    private fun getAllUserRequest(){
        riderViewModel.getAllUserRequest(0,"Bearer ${preferences.getToken()}")
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}