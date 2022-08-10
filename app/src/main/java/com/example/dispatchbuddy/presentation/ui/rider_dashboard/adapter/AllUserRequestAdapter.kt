package com.example.dispatchbuddy.presentation.ui.rider_dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.data.remote.dto.models.allRequestModels.AllUserRequestResponseContent
import com.example.dispatchbuddy.databinding.RequestsRvItemBinding

class AllUserRequestAdapter (
    private val context: Context,
    private val allUserRequestList: ArrayList<AllUserRequestResponseContent>,
    private val onItemClick: (AllUserRequestResponseContent) -> Unit
): RecyclerView.Adapter<AllUserRequestAdapter.ViewHolder>() {
//    private val allUserRequestList: ArrayList<AllUserRequestResponseContent> = ArrayList()
    inner class ViewHolder(
        val binding: RequestsRvItemBinding,
        private val onItemClick: (AllUserRequestResponseContent) -> Unit
    ): RecyclerView.ViewHolder(binding.root)  {
        fun bindViews(riderResponse: AllUserRequestResponseContent){
            when(riderResponse.status){
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
            binding.requesterNameTv.text = riderResponse.userName
            binding.requesterLocationTv.text = riderResponse.pickupLocation
            binding.itemWeightTv.text = riderResponse.userPhoneNumber
            binding.riderLayout.setOnClickListener {
                onItemClick.invoke(riderResponse)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RequestsRvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(allUserRequestList[position])
    }

    override fun getItemCount(): Int {
        return allUserRequestList.size
    }

}

// object DiffCallback : DiffUtil.ItemCallback<AllUserRequestResponseContent>() {
//    override fun areItemsTheSame(oldItem: AllUserRequestResponseContent, newItem: AllUserRequestResponseContent) =
//        oldItem.id == newItem.id
//    override fun areContentsTheSame(oldItem: AllUserRequestResponseContent, newItem: AllUserRequestResponseContent) =
//        oldItem == newItem
//}

