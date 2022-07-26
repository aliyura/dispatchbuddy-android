package com.example.dispatchbuddy.presentation.ui.rider_dashboard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.aWeekAgo
import com.example.dispatchbuddy.common.dummyData
import com.example.dispatchbuddy.common.requestList
import com.example.dispatchbuddy.common.yesterday
import com.example.dispatchbuddy.data.remote.dto.RiderResponse
import com.example.dispatchbuddy.data.remote.dto.RiderSectionResponse
import com.example.dispatchbuddy.data.remote.dto.RiderSectionResponse2
import com.example.dispatchbuddy.databinding.FragmentRequestBinding
import com.example.dispatchbuddy.presentation.ui.rider_dashboard.adapter.RequestAdapter
import com.example.dispatchbuddy.presentation.ui.rider_dashboard.adapter.RequestListAdapter

class RequestFragment : Fragment() {
    private var _binding: FragmentRequestBinding? = null
    private val binding get() = _binding!!
    private lateinit var requestAdapter: RequestAdapter
    private val responseList: ArrayList<RiderResponse> = ArrayList()
    private val sectionResponse: ArrayList<RiderSectionResponse> = ArrayList()
//    lateinit var requestListAdapter: RequestListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRequestBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateData(dummyData)
        initRecyclerview()
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}