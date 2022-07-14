package com.example.dispatchbuddy.presentation.ui.user_dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.data.remote.dto.RiderProfile
import com.example.dispatchbuddy.databinding.RidersListRvItemBinding

class RiderListAdapter : ListAdapter<RiderProfile, RiderListAdapter.ViewHolder>(DiffCallBack) {
    class ViewHolder(val binding: RidersListRvItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(riderProfile: RiderProfile) {
            binding.apply {
                riderNameTv.text = riderProfile.name
                riderLocationTv.text = riderProfile.location
                riderPhoneNumberTv.text = riderProfile.mobile
                riderDateJoinedTv.text = riderProfile.dateJoined
                Glide.with(binding.root.context)
                    .load(riderProfile.image)
                    .placeholder(R.drawable.profile_avatar)
                    .into(riderProfileImageIv)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RidersListRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object DiffCallBack : DiffUtil.ItemCallback<RiderProfile>() {
    override fun areItemsTheSame(oldItem: RiderProfile, newItem: RiderProfile): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: RiderProfile, newItem: RiderProfile): Boolean {
        return oldItem == newItem
    }
}
