package com.example.dispatchbuddy.presentation.ui.rider_dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dispatchbuddy.data.remote.dto.models.allRequestModels.AllUserRequestResponseContent
import com.example.dispatchbuddy.databinding.DeliveriesRvItemBinding

class ClosedRequestAdapter (
    private val closedUserRequestList: ArrayList<AllUserRequestResponseContent>,
    private val onItemClick: (AllUserRequestResponseContent) -> Unit
): RecyclerView.Adapter<ClosedRequestAdapter.ViewHolder>() {
    inner class ViewHolder(
        val binding: DeliveriesRvItemBinding,
        private val onItemClick: (AllUserRequestResponseContent) -> Unit
    ): RecyclerView.ViewHolder(binding.root)  {
        fun bindViews(riderResponse: AllUserRequestResponseContent){
            binding.riderNameTv.text = riderResponse.userName
            binding.riderLocationTv.text = riderResponse.pickupLocation
            binding.itemWeightTv.text = riderResponse.userPhoneNumber
            binding.riderLayout.setOnClickListener {
                onItemClick.invoke(riderResponse)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DeliveriesRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(closedUserRequestList[position])
    }

    override fun getItemCount(): Int {
        return closedUserRequestList.size
    }

}

