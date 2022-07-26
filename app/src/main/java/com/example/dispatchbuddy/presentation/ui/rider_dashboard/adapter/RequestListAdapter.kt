package com.example.dispatchbuddy.presentation.ui.rider_dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dispatchbuddy.data.remote.dto.RiderResponse
import com.example.dispatchbuddy.data.remote.dto.RiderSectionResponse
import com.example.dispatchbuddy.databinding.RequestRvHeaderBinding
import java.text.SimpleDateFormat
import java.util.*

class RequestListAdapter(val context: Context) : ListAdapter<RiderSectionResponse, RequestListAdapter.ViewHolder>(DiffCallBack) {
    inner class ViewHolder(val binding: RequestRvHeaderBinding): RecyclerView.ViewHolder(binding.root) {
        val headerText = binding.headerTitleTv
        val childRequestRv = binding.fragmentRequestChildRv
        fun bindViews(request: RiderSectionResponse){
            binding.fragmentRequestChildRv.apply {
                val childListAdapter = RequestChildListAdapter(context)
                childListAdapter.addChildList(request.riderResponse)
                adapter = childListAdapter
                val stackedLayoutManager= LinearLayoutManager(context)
                layoutManager = stackedLayoutManager
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RequestRvHeaderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parentItem = getItem(position)
        holder.bindViews(parentItem)
        val dateString = parentItem.sectionHeaders[position].toString()
        val myDate: Date = SimpleDateFormat("yyyy-M-d", Locale.ENGLISH).parse(dateString)!!
        val calendar = Calendar.getInstance()
        calendar.time = myDate
        when(calendar[Calendar.DAY_OF_WEEK]){
            1->{holder.headerText.text = "Sunday, ${calendar.time}"}
            2->{holder.headerText.text = "Monday, ${calendar.time}"}
            3->{holder.headerText.text = "Tuesday, ${calendar.time}"}
            4->{holder.headerText.text = "Wednesday, ${calendar.time}"}
            5->{holder.headerText.text = "Thursday, ${calendar.time}"}
            6->{holder.headerText.text = "Friday, ${calendar.time}"}
            7->{holder.headerText.text = "Saturday, ${calendar.time}"}
        }
        val sessionList: ArrayList<RiderResponse> = ArrayList(parentItem.riderResponse)
        val layoutManager = LinearLayoutManager(holder.childRequestRv.context, LinearLayoutManager.VERTICAL, false)
        layoutManager.initialPrefetchItemCount = parentItem.riderResponse.size
        val childAdapter = RequestChildListAdapter(context)
        childAdapter.addChildList(sessionList)
        holder.childRequestRv.layoutManager = layoutManager
        holder.childRequestRv.adapter = childAdapter
    }

   object DiffCallBack : DiffUtil.ItemCallback<RiderSectionResponse>(){
       override fun areItemsTheSame(oldItem: RiderSectionResponse, newItem: RiderSectionResponse): Boolean {
           return oldItem == newItem
       }
       override fun areContentsTheSame(oldItem: RiderSectionResponse, newItem: RiderSectionResponse): Boolean {
           return oldItem == newItem
       }
   }
}