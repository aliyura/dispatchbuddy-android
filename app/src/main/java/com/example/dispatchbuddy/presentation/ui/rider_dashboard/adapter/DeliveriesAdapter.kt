package com.example.dispatchbuddy.presentation.ui.rider_dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispatchbuddy.data.remote.dto.DeliverySectionResponse
import com.example.dispatchbuddy.databinding.DeliveriesRvHeaderBinding

class DeliveriesAdapter(
    private val deliveriesList: ArrayList<DeliverySectionResponse>
): RecyclerView.Adapter<DeliveriesAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: DeliveriesRvHeaderBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindViews(items: DeliverySectionResponse){
            binding.headerTitleTv.text = items.date
            bindDeliveriesView(items)
        }
        private fun bindDeliveriesView(deliveries: DeliverySectionResponse){
            binding.fragmentDeliveriesChildRv.apply {
                val childAdapter = DeliveriesChildAdapter()
                childAdapter.addChildList(deliveries.deliveriesResponse)
                adapter = childAdapter
                val stackedLayoutManager= LinearLayoutManager(context)
                layoutManager = stackedLayoutManager
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DeliveriesRvHeaderBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsList = deliveriesList[position]
        holder.bindViews(itemsList)
    }

    override fun getItemCount(): Int {
        return deliveriesList.size
    }
}