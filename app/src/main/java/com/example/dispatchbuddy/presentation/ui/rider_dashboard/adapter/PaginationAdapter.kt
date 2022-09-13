package com.example.dispatchbuddy.presentation.ui.rider_dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.data.remote.dto.models.allRequestModels.AllUserRequestResponseContent
import com.example.dispatchbuddy.databinding.RequestsRvItemBinding
import java.text.SimpleDateFormat
import java.util.*

class PaginationAdapter(
    private val onItemClick: (AllUserRequestResponseContent) -> Unit
): PagingDataAdapter<AllUserRequestResponseContent, PaginationAdapter.MyViewHolder>(DiffUtilCallBack()) {
    class MyViewHolder(
        val binding: RequestsRvItemBinding,
        private val onItemClick: (AllUserRequestResponseContent) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
        private val context = binding.root.context
        fun bind(riderResponse: AllUserRequestResponseContent?){

            when(riderResponse?.status){
                "AC"->{
                    binding.requestStatus.text = context.getString(R.string.accepted)
                    binding.requestStatus.setTextColor(ContextCompat.getColor(context, R.color.cadmium_green))
                    binding.requestStatus.background = AppCompatResources.getDrawable(context, R.drawable.delivery_status_background_completed)
                }
                "RJ"->{
                    binding.requestStatus.text = context.getString(R.string.rejected)
                    binding.requestStatus.setTextColor(ContextCompat.getColor(context, R.color.rufous_red))
                    binding.requestStatus.background = AppCompatResources.getDrawable(context, R.drawable.delivery_status_background_canceled)
                }
                "CO"->{
                    binding.requestStatus.text = context.getString(R.string.completed)
                    binding.requestStatus.setTextColor(ContextCompat.getColor(context, R.color.white))
                    binding.requestStatus.background = AppCompatResources.getDrawable(context, R.drawable.delivery_status_background_complete)
                }
                else ->{
                    binding.requestStatus.text = context.getString(R.string.pending)
                    binding.requestStatus.setTextColor(ContextCompat.getColor(context, R.color.request_status_color))
                    binding.requestStatus.background = AppCompatResources.getDrawable(context, R.drawable.delivery_status_background_ongoing)
                }
            }
            binding.requesterNameTv.text = riderResponse?.userName
            binding.requesterLocationTv.text = riderResponse?.pickupLocation
            binding.itemWeightTv.text = riderResponse?.userPhoneNumber
            binding.riderLayout.setOnClickListener {
                if (riderResponse != null) {
                    onItemClick.invoke(riderResponse)
                }
            }
            val dateString = riderResponse?.createdDate
            val myDate: Date = SimpleDateFormat("yyyy-M-d", Locale.ENGLISH).parse(dateString!!)!!
            val newDate = myDate.toString().substring(0,10) +" "+ myDate.toString().substring(29,34)
            binding.requesterDateTv.text = newDate
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RequestsRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding, onItemClick)
    }
    class DiffUtilCallBack: DiffUtil.ItemCallback<AllUserRequestResponseContent>(){
        override fun areItemsTheSame(
            oldItem: AllUserRequestResponseContent,
            newItem: AllUserRequestResponseContent
        ): Boolean {
            return oldItem.userName == oldItem.userName
        }

        override fun areContentsTheSame(
            oldItem: AllUserRequestResponseContent,
            newItem: AllUserRequestResponseContent
        ): Boolean {
            return oldItem.userName == oldItem.userName && oldItem.requestId == oldItem.requestId
        }
    }
}