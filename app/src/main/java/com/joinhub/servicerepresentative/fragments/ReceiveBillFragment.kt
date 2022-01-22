package com.joinhub.servicerepresentative.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.joinhub.servicerepresentative.activities.DashboardActivity
import com.joinhub.servicerepresentative.databinding.FragmentReceiveBillBinding
import com.joinhub.servicerepresentative.viewPagerAdapter.RecBillPagerAdapter

private val tabArray = arrayOf(
    "Receive Bills",
    "Billing History"
)

class ReceiveBillFragment : Fragment() {

    private var _binding: FragmentReceiveBillBinding? = null
    lateinit var adapter: RecBillPagerAdapter
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReceiveBillBinding.inflate(inflater, container, false)
        settingUpViewPager()
        DashboardActivity.textView.text="Receive Bills"
        return binding.root
    }


    private fun settingUpViewPager() {
        adapter = RecBillPagerAdapter(
            activity?.supportFragmentManager!!,
            activity?.lifecycle!!
        )
        binding.billingViewPager.adapter= adapter
        TabLayoutMediator(binding.tabLayoutBill,binding.billingViewPager) { tab, position ->
            tab.text = tabArray[position]
          //  tab.icon= ContextCompat.getDrawable(requireActivity(), tabIconArray[position]);
        }.attach()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}