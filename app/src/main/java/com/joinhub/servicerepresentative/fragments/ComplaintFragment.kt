package com.joinhub.servicerepresentative.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.joinhub.servicerepresentative.activities.DashboardActivity
import com.joinhub.servicerepresentative.databinding.FragmentComplaintBinding



class ComplaintFragment : Fragment() {


    private var _binding: FragmentComplaintBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentComplaintBinding.inflate(inflater, container, false)
        init()

        return binding.root
    }

    private fun init() {
        DashboardActivity.textView.text= "Complaints"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}