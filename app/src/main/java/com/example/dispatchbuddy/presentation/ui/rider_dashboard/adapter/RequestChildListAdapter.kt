package com.example.dispatchbuddy.presentation.ui.rider_dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.data.remote.dto.RiderResponse
import com.example.dispatchbuddy.databinding.RequestsRvItemBinding

class RequestChildListAdapter(
    private val context: Context
    ): ListAdapter<RiderResponse, RequestChildListAdapter.ViewHolder2>(DiffCallBack) {
    private val requestChildList: ArrayList<RiderResponse> = arrayListOf()
    inner class ViewHolder2(val binding: RequestsRvItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindViews(riderResponse: RiderResponse){
            when(riderResponse.status){
                "completed"->{
                    binding.requestStatus.text = context.getString(R.string.completed)
                    binding.requestStatus.setTextColor(ContextCompat.getColor(context, R.color.cadmium_green))
                    binding.requestStatus.background = AppCompatResources.getDrawable(context, R.drawable.delivery_status_background_completed)
                }
                "cancelled"->{
                    binding.requestStatus.text = context.getString(R.string.cancelled)
                    binding.requestStatus.setTextColor(ContextCompat.getColor(context, R.color.rufous_red))
                    binding.requestStatus.background = AppCompatResources.getDrawable(context, R.drawable.delivery_status_background_canceled)
                }
                else ->{
                    binding.requestStatus.text = context.getString(R.string.ongoing)
                    binding.requestStatus.setTextColor(ContextCompat.getColor(context, R.color.request_status_color))
                    binding.requestStatus.background = AppCompatResources.getDrawable(context, R.drawable.delivery_status_background_ongoing)
                }
            }
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
    fun addChildList(list: List<RiderResponse>){
        requestChildList.clear()
        requestChildList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestChildListAdapter.ViewHolder2 {
        val binding = RequestsRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder2(binding)
    }

    override fun onBindViewHolder(holder: RequestChildListAdapter.ViewHolder2, position: Int) {
        holder.bindViews(getItem(position))
    }
    object DiffCallBack : DiffUtil.ItemCallback<RiderResponse>() {
        override fun areItemsTheSame(oldItem: RiderResponse, newItem: RiderResponse): Boolean {
            return oldItem.name == newItem.name
        }
        override fun areContentsTheSame(oldItem: RiderResponse, newItem: RiderResponse): Boolean {
            return oldItem == newItem
        }
    }
}