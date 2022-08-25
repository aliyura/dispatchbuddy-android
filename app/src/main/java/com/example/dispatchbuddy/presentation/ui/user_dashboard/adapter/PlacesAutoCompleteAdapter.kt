package com.example.dispatchbuddy.presentation.ui.user_dashboard.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dispatchbuddy.databinding.LocationResultRvItemBinding
import com.example.dispatchbuddy.databinding.PlacesAutocompleteRvItemBinding
import com.google.android.libraries.places.api.model.AutocompletePrediction

class PlacesAutoCompleteAdapter(
    private val onItemClick: (AutocompletePrediction) -> Unit
) : ListAdapter<AutocompletePrediction, PlacesAutoCompleteAdapter.ViewHolder>(PlacesDiffCallBack) {

    class ViewHolder(
        val binding: LocationResultRvItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(placeAutocomplete: AutocompletePrediction, onItemClick: (AutocompletePrediction) -> Unit) {
            with(binding) {
                locationButton.text = placeAutocomplete.getPrimaryText(null)
                locationButton.setOnClickListener {
                    onItemClick.invoke(placeAutocomplete)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LocationResultRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }


}

object PlacesDiffCallBack : DiffUtil.ItemCallback<AutocompletePrediction>() {
    override fun areItemsTheSame(oldItem: AutocompletePrediction, newItem: AutocompletePrediction): Boolean {
        return oldItem.placeId == newItem.placeId
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: AutocompletePrediction, newItem: AutocompletePrediction): Boolean {
        return newItem == oldItem
    }
}

