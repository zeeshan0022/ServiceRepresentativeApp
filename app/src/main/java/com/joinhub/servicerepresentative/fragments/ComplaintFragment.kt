package com.joinhub.servicerepresentative.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joinhub.complaintprotaluser.models.ComplaintModel
import com.joinhub.servicerepresentative.activities.DashboardActivity
import com.joinhub.servicerepresentative.adapter.ComplaintAdapter
import com.joinhub.servicerepresentative.databinding.FragmentComplaintBinding

val type = arrayOf("All","Active", "Solved", "Rejected", "Working","Cancelled")
class ComplaintFragment : Fragment() {
    private lateinit var adapter: ComplaintAdapter
    private var listComplaint:MutableList<ComplaintModel> = mutableListOf()
    private var _binding: FragmentComplaintBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentComplaintBinding.inflate(inflater, container, false)
        init()

        if(HomeFragment.list1.isEmpty()){

        }else{
            setUpRece(HomeFragment.list1)
        }
        binding.filledExposedDropdown.setOnItemClickListener { _, _, _, _ ->
            when {
                binding.filledExposedDropdown.text.toString()=="All" -> {
                    setUpRece(HomeFragment.list1)
                }
                binding.filledExposedDropdown.text.toString()=="Active" -> {
                    filterData("Active")
                }
                binding.filledExposedDropdown.text.toString()=="Solved" -> {
                    filterData("Solved")
                }
                binding.filledExposedDropdown.text.toString()=="Rejected" -> {
                    filterData("Rejected")
                }
                binding.filledExposedDropdown.text.toString()=="Working" -> {
                    filterData("Working")
                }
                binding.filledExposedDropdown.text.toString()=="Cancelled" -> {
                    filterData("Cancelled")
                }
            }
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s!!.isBlank()){

                    setUpRece(HomeFragment.list1)
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               if(s.toString().isNotBlank()) {
                   filterData(s.toString())
               }else{
                   binding.recyclerComplaint.visibility= VISIBLE
                   setUpRece(HomeFragment.list1)
               }
            }
        })
        return binding.root
    }

    private fun filterData(s: String) {
        listComplaint.clear()
        for (model in HomeFragment.list1){
            if(model.complaintStatus.equals(s,true)){
                listComplaint.add(model)
            }
        }
        if(listComplaint.isEmpty()){
            binding.recyclerComplaint.visibility=GONE

        }else{
            binding.recyclerComplaint.visibility= VISIBLE
            setUpRece(listComplaint)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
      //  DashboardActivity.textView.text= "Complaints"
        val adapter = ArrayAdapter(requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            type
        )
        binding.filledExposedDropdown.setAdapter(adapter)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun setUpRece(list1: MutableList<ComplaintModel>) {
        adapter= ComplaintAdapter(requireActivity(),list1, "a")
        binding.recyclerComplaint.layoutManager= LinearLayoutManager(requireContext(),
            RecyclerView.VERTICAL,false)
        binding.recyclerComplaint.adapter= adapter

    }
}