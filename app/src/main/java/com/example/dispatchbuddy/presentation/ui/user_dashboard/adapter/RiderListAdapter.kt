package com.example.dispatchbuddy.presentation.ui.user_dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.data.remote.dto.models.riderRequestModel.RequestRiderContent
import com.example.dispatchbuddy.databinding.RidersListRvItemBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class RiderListAdapter(
    private val onItemClick: (RequestRiderContent) -> Unit
) : ListAdapter<RequestRiderContent, RiderListAdapter.ViewHolder>(DiffCallBack) {

    class ViewHolder(
        val binding: RidersListRvItemBinding,
        private val onItemClick: (RequestRiderContent) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(riderProfile: RequestRiderContent) {
            binding.apply {
                riderNameTv.text = riderProfile.name
                riderLocationTv.text = riderProfile.city
                riderPhoneNumberTv.text = riderProfile.phoneNumber
                val dateString = riderProfile.createdDate
                riderDateJoinedTv.text = "Joined, ${formattedDate(dateString = dateString)}"
                Glide.with(binding.root.context)
                    .load(riderProfile.dp)
                    .placeholder(R.drawable.profile_avatar)
                    .into(riderProfileImageIv)
                contactRidersButton.setOnClickListener {
                    onItemClick.invoke(riderProfile)
                }
            }
        }
        private fun formattedDate(dateString: String): String{
            var formattedDate = ""
            val parserFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH)
            val convertFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH)
            var date: Date? = null
            try {
                date = parserFormat.parse(dateString)
                if (date != null) {
                    formattedDate = convertFormat.format(date)
                }
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return formattedDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RidersListRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

object DiffCallBack : DiffUtil.ItemCallback<RequestRiderContent>() {
    override fun areItemsTheSame(oldItem: RequestRiderContent, newItem: RequestRiderContent): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: RequestRiderContent, newItem: RequestRiderContent): Boolean {
        return oldItem == newItem
    }

}
