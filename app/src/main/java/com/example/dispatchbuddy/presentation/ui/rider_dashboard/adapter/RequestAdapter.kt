package com.example.dispatchbuddy.presentation.ui.rider_dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.data.remote.dto.RiderResponse
import com.example.dispatchbuddy.data.remote.dto.RiderSectionResponse
import com.example.dispatchbuddy.data.remote.dto.RiderSectionResponse2
import com.example.dispatchbuddy.databinding.RequestRvHeaderBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RequestAdapter(
    private val requestList: ArrayList<RiderSectionResponse2>,
//    private val context: Context
    ): RecyclerView.Adapter<RequestAdapter.ViewHolder>() {
//    var newList: ArrayList<RiderResponse> = ArrayList()
//    private var viewPool = RecyclerView.RecycledViewPool()
    inner class ViewHolder(val binding: RequestRvHeaderBinding):RecyclerView.ViewHolder(binding.root) {
//        val headerText = binding.headerTitleTv
//        val childRequestRv = binding.fragmentRequestChildRv
        fun bindViews(items: RiderSectionResponse2){
            binding.headerTitleTv.text = items.date
            bindRiderView(items)
        }
        private fun bindRiderView(request: RiderSectionResponse2){
            binding.fragmentRequestChildRv.apply {
                val childAdapter = RequestChildAdapter(context)
                childAdapter.addChildList(request.riderResponse)
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parentItem = requestList[position]
        holder.bindViews(parentItem)
//        val dateString = parentItem.sectionHeaders[position].toString()
//        val myDate: Date = SimpleDateFormat("yyyy-m-d", Locale.ENGLISH).parse(dateString)!!
//        val calendar = Calendar.getInstance()
//        calendar.time = myDate
//        when(calendar[Calendar.DAY_OF_WEEK]){
//            1->{holder.headerText.text = context.getString(R.string.sunday)}
//            2->{holder.headerText.text = context.getString(R.string.monday)}
//            3->{holder.headerText.text = context.getString(R.string.tuesday)}
//            4->{holder.headerText.text = context.getString(R.string.wednesday)}
//            5->{holder.headerText.text = context.getString(R.string.thursday)}
//            6->{holder.headerText.text = context.getString(R.string.friday)}
//            7->{holder.headerText.text = context.getString(R.string.saturday)}
//        }
//        val sessionList: ArrayList<RiderResponse> = ArrayList(parentItem.riderResponse)
//        val layoutManager = LinearLayoutManager(holder.childRequestRv.context, LinearLayoutManager.VERTICAL, false)
//        layoutManager.initialPrefetchItemCount = parentItem.riderResponse.size
//        val childAdapter = RequestChildAdapter(sessionList, context)
//        holder.childRequestRv.layoutManager = layoutManager
//        holder.childRequestRv.adapter = childAdapter
    }
    override fun getItemCount(): Int {
        return requestList.size
    }
}
