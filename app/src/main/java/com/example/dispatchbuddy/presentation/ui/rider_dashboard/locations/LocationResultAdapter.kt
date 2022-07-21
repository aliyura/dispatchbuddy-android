package com.example.dispatchbuddy.presentation.ui.rider_dashboard.locations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dispatchbuddy.data.remote.dto.Locations
import com.example.dispatchbuddy.databinding.LocationResultRvItemBinding

class LocationResultAdapter(private val onItemClick: (Locations) -> Unit) :
    ListAdapter<Locations, LocationResultAdapter.ViewHolder>(DiffCallBack) {

    class ViewHolder(
        val binding: LocationResultRvItemBinding,
        private val onItemClick: (Locations) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(locations: Locations) {
            binding.apply {
                locationButton.text = locations.cityName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            LocationResultRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object DiffCallBack : DiffUtil.ItemCallback<Locations>() {
    override fun areItemsTheSame(oldItem: Locations, newItem: Locations): Boolean {
        return oldItem.cityName == newItem.cityName
    }

    override fun areContentsTheSame(oldItem: Locations, newItem: Locations): Boolean {
        return oldItem == newItem
    }
}
