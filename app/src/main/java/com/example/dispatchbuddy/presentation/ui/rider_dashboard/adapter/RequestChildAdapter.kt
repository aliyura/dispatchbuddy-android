package com.example.dispatchbuddy.presentation.ui.rider_dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.data.remote.dto.RiderResponse
import com.example.dispatchbuddy.databinding.RequestsRvItemBinding

class RequestChildAdapter(
//    private val requestChildList: ArrayList<RiderResponse>,
    private val context: Context
): RecyclerView.Adapter<RequestChildAdapter.ViewHolder2>(){
    private val requestChildList: ArrayList<RiderResponse> = arrayListOf()
    inner class ViewHolder2(val binding: RequestsRvItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindViews(riderResponse: RiderResponse){
            binding.requestStatus.text = riderResponse.status
            binding.requesterNameTv.text = riderResponse.name
            binding.requesterLocationTv.text = riderResponse.location
            binding.itemWeightTv.text = riderResponse.weight
            binding.chargedAmountTv.text = riderResponse.charge
            Glide.with(context)
                .load(riderResponse.image)
                .placeholder(R.drawable.profile_avatar)
                .into(binding.requestRiderAvatar)
        }
    }
    fun addChildList(list: ArrayList<RiderResponse>){
        requestChildList.clear()
        requestChildList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder2 {
        val binding = RequestsRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder2(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder2, position: Int) {
        holder.bindViews(requestChildList[position])
    }

    override fun getItemCount(): Int {
        return requestChildList.size
    }

}