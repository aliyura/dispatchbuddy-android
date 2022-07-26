package com.example.dispatchbuddy.presentation.ui.rider_dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.deliveriesList
import com.example.dispatchbuddy.data.remote.dto.DeliverySectionResponse
import com.example.dispatchbuddy.databinding.FragmentDeliveriesBinding
import com.example.dispatchbuddy.presentation.ui.rider_dashboard.adapter.DeliveriesAdapter

class DeliveriesFragment : Fragment() {
    private var _binding: FragmentDeliveriesBinding? = null
    private val binding get() = _binding!!
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
        initRecyclerview()
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}