package com.example.dispatchbuddy.presentation.ui.rider_dashboard.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispatchbuddy.common.getDaysAgo
import com.example.dispatchbuddy.data.remote.dto.RiderResponse
import com.example.dispatchbuddy.data.remote.dto.RiderSectionResponse
import com.example.dispatchbuddy.databinding.RequestRvHeaderBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class RequestAdapter(
    private val requestList: ArrayList<RiderSectionResponse>,
    private val context: Context
    ): RecyclerView.Adapter<RequestAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RequestRvHeaderBinding):RecyclerView.ViewHolder(binding.root) {
        val headerText = binding.headerTitleTv
        val childRequestRv = binding.fragmentRequestChildRv
        fun bindRiderView(request: RiderSectionResponse){
            binding.fragmentRequestChildRv.apply {
                val childAdapter = RequestChildAdapter(request.riderResponse, context)
                adapter = childAdapter
                val stackedLayoutManager= LinearLayoutManager(context)
                layoutManager = stackedLayoutManager
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RequestRvHeaderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parentItem = requestList[position]
        holder.bindRiderView(parentItem)
        val dateString = parentItem.sectionHeaders[position].toString()
        val myDate: Date = SimpleDateFormat("yyyy-M-d", Locale.ENGLISH).parse(dateString)!!

        val calendar = Calendar.getInstance()
        val dateTime = LocalDateTime.now()
        dateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))
        calendar.time = myDate
        when (calendar.time) {
            getDaysAgo(0) -> {
                holder.headerText.text = "$dateTime"
            }
            getDaysAgo(-1) -> {
                holder.headerText.text = "Yesterday"
            }
            getDaysAgo(-7) -> {
                holder.headerText.text = "A week ago"
            }
            else -> {
                holder.headerText.text = "All History"
            }
        }
        val sessionList: ArrayList<RiderResponse> = ArrayList(parentItem.riderResponse)
        val layoutManager = LinearLayoutManager(holder.childRequestRv.context, LinearLayoutManager.VERTICAL, false)
        layoutManager.initialPrefetchItemCount = parentItem.riderResponse.size
        val childAdapter = RequestChildAdapter(sessionList, context)
        holder.childRequestRv.layoutManager = layoutManager
        holder.childRequestRv.adapter = childAdapter
    }
    override fun getItemCount(): Int {
        return requestList.size
    }
}
