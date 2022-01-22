package com.joinhub.servicerepresentative.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.YAxis
import com.joinhub.servicerepresentative.R
import com.joinhub.servicerepresentative.activities.ComplaintInfoActivity
import com.joinhub.servicerepresentative.activities.DashboardActivity
import com.joinhub.servicerepresentative.databinding.FragmentHomeBinding
import com.github.mikephil.charting.data.BarData

import com.github.mikephil.charting.data.BarDataSet

import com.github.mikephil.charting.data.BarEntry





class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        init()
        binding.txtViewAllComplaint.setOnClickListener{
           DashboardActivity.navController.navigate(R.id.navigation_dashboard)
        }
        binding.moreInfo.setOnClickListener{
            startActivity(Intent(context, ComplaintInfoActivity::class.java))
        }

        return binding.root
    }

    private fun init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            showBarChart()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showBarChart() {
        val valueList = ArrayList<Double>()
        val entries: ArrayList<BarEntry> = ArrayList()
        //input data
        for (i in 0..5) {
            valueList.add(i * 100.1)
        }

        //fit the data into a bar
        for (i in 0 until valueList.size) {
            val barEntry =BarEntry(i.toFloat(), valueList[i].toFloat())
            entries.add(barEntry)
        }
        val barDataSet = BarDataSet(entries, "")
        val data = BarData(barDataSet)

        barDataSet.color= requireContext().getColor(R.color.colorSecDark)
        binding.activeBarchart.data = data
        barDataSet.valueTextColor= requireContext().getColor(R.color.colorSecDark)
        binding.activeBarchart.setViewPortOffsets(0F,0F,0F,0F)
        binding.activeBarchart.moveViewTo(0f,0f, YAxis.AxisDependency.RIGHT)

        binding.activeBarchart.invalidate();
        binding.activeBarchart.setFitBars(true)
        binding.activeBarchart.description.isEnabled=false   // Hide the description
        binding.activeBarchart.axisLeft.setDrawLabels(false)
        binding.activeBarchart.axisRight.setDrawLabels(false)
        binding.activeBarchart.xAxis.setDrawLabels(false)
        binding.activeBarchart.axisLeft.setDrawGridLines(false);
        binding.activeBarchart.xAxis.setDrawGridLines(false);
        binding.activeBarchart.axisRight.setDrawGridLines(false);
        binding.activeBarchart.legend.isEnabled = false  // Hide the legend
    }
    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        DashboardActivity.textView.text= "Dashboard"
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}