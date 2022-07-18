package com.example.dispatchbuddy.presentation.ui.user_dashboard

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.ridersList
import com.example.dispatchbuddy.databinding.FragmentRidersListBinding
import com.example.dispatchbuddy.presentation.ui.user_dashboard.adapter.RiderListAdapter

class RidersListFragment : Fragment() {
    private var _binding: FragmentRidersListBinding? = null
    private val binding get() = _binding!!
    lateinit var riderListAdapter: RiderListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRidersListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        riderListAdapter = RiderListAdapter {
            val action =
                RidersListFragmentDirections.actionRidersListFragmentToSuccessDialogFragment(it)
            findNavController().navigate(action)
        }
        binding.ridersListRv.adapter = riderListAdapter
        riderListAdapter.submitList(ridersList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}