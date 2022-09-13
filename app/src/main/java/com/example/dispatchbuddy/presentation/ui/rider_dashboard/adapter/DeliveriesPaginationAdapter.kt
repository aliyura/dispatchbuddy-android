package com.example.dispatchbuddy.presentation.ui.rider_dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dispatchbuddy.data.remote.dto.models.allRequestModels.AllUserRequestResponseContent
import com.example.dispatchbuddy.databinding.DeliveriesRvItemBinding
import java.text.SimpleDateFormat
import java.util.*

class DeliveriesPaginationAdapter(
    private val onItemClick: (AllUserRequestResponseContent) -> Unit
): PagingDataAdapter<AllUserRequestResponseContent, DeliveriesPaginationAdapter.MyViewHolder>(DiffUtilCallBack()) {
    class MyViewHolder(
        val binding: DeliveriesRvItemBinding,
        private val onItemClick: (AllUserRequestResponseContent) -> Unit
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(riderResponse: AllUserRequestResponseContent?){
            with(binding){
                riderNameTv.text = riderResponse?.userName
                riderLocationTv.text = riderResponse?.pickupLocation
                itemWeightTv.text = riderResponse?.destination
                chargedAmountTv.text = riderResponse?.userPhoneNumber
                rateDeliveryBtn.setOnClickListener {
                    if (riderResponse != null) {
                        onItemClick.invoke(riderResponse)
                    }
                }
                val dateString = riderResponse?.createdDate
                val myDate: Date = SimpleDateFormat("yyyy-M-d", Locale.ENGLISH).parse(dateString!!)!!
                val newDate = myDate.toString().substring(0,10) +" "+ myDate.toString().substring(29,34)
                binding.riderDateTv.text = newDate
            }
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DeliveriesRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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