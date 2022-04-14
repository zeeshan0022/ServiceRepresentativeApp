package com.joinhub.servicerepresentative.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.joinhub.complaintprotaluser.models.BillingModel
import com.joinhub.servicerepresentative.R
import com.joinhub.servicerepresentative.databinding.FragmentBillingHistoryBinding
import com.joinhub.servicerepresentative.interfaces.LoadDataListInterface


class BillingHistoryFragment : Fragment() , LoadDataListInterface<BillingModel>{
    lateinit var binding:FragmentBillingHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentBillingHistoryBinding.inflate(inflater,container, false)

        return binding.root
    }

    override fun onStarts() {
        binding.progressBar.visibility= View.VISIBLE
    }

    override fun onSuccess(list: MutableList<BillingModel>) {
        binding.progressBar.visibility= View.GONE
    }

    override fun onError(e: String) {
        binding.progressBar.visibility= View.GONE
    }

}