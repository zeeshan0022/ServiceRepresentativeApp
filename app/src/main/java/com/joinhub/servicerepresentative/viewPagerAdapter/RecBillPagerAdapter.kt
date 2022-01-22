package com.joinhub.servicerepresentative.viewPagerAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.joinhub.servicerepresentative.fragments.BillingFragment
import com.joinhub.servicerepresentative.fragments.BillingHistoryFragment

class RecBillPagerAdapter(fragmentManager: FragmentManager, lifeCycle: Lifecycle) : FragmentStateAdapter(fragmentManager,lifeCycle) {
    private val NUM_PAGES=2
    override fun getItemCount(): Int {

        return NUM_PAGES;
    }

    override fun createFragment(position: Int): Fragment {

        when(position){
            0->  return BillingFragment()
            1-> return BillingHistoryFragment()

        }
        return BillingFragment()
    }
}