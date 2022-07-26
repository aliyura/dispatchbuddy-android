package com.example.dispatchbuddy.presentation.ui.rider_dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dispatchbuddy.data.remote.dto.DeliveriesResponse
import com.example.dispatchbuddy.databinding.DeliveriesRvItemBinding

class DeliveriesChildAdapter: RecyclerView.Adapter<DeliveriesChildAdapter.ViewHolder>() {
    private val deliveriesChildList: ArrayList<DeliveriesResponse> = ArrayList()

    inner class ViewHolder(val binding: DeliveriesRvItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindViews(items: DeliveriesResponse){
            binding.riderNameTv.text = items.name
            binding.riderLocationTv.text = items.location
            binding.itemWeightTv.text = items.weight
            binding.chargedAmountTv.text = items.amount
        }
    }
    fun addChildList(list: ArrayList<DeliveriesResponse>){
        deliveriesChildList.clear()
        deliveriesChildList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DeliveriesRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(deliveriesChildList[position])
    }

    override fun getItemCount(): Int {
        return deliveriesChildList.size
    }

}