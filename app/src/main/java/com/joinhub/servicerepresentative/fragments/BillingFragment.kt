package com.joinhub.servicerepresentative.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joinhub.alphavpn.utility.Preference
import com.joinhub.complaintprotaluser.models.BillingModel
import com.joinhub.complaintprotaluser.models.PackageDetails
import com.joinhub.complaintprotaluser.models.UserModel
import com.joinhub.servicerepresentative.R
import com.joinhub.servicerepresentative.adapter.BillingAdapter
import com.joinhub.servicerepresentative.databinding.FragmentBillingBinding
import com.joinhub.servicerepresentative.interfaces.BillingInterface
import com.joinhub.servicerepresentative.presenatator.BillingPresentator

class BillingFragment : Fragment() , BillingInterface{
    lateinit var binding:FragmentBillingBinding
    lateinit var presentator:BillingPresentator
    lateinit var list:MutableList<UserModel>
    lateinit var preference: Preference
    companion object{
        lateinit var userList1:MutableList<UserModel>
        lateinit var  pkgList1: MutableList<PackageDetails>
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentBillingBinding.inflate(inflater,container,false)
        pkgList1= mutableListOf()
        list= mutableListOf()
        preference= Preference(requireContext())
        presentator= BillingPresentator(this, requireActivity())
        userList1= mutableListOf()
        if(userList1.isEmpty()){
            presentator.loadData(preference.getIntpreference("areaID"))
        }else{
            setUpRec(userList1, pkgList1)
        }
     binding.searchUser.addTextChangedListener(object : TextWatcher{
         override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

         }

         override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

         override fun afterTextChanged(s: Editable?) {
            if(s!!.isBlank()){
                setUpRec(userList1, pkgList1)
            }else{
                filterData(s.toString())
            }
         }

     })
        return binding.root
    }
    private fun filterData(s: String) {
        list.clear()
        for (model in userList1){
            if(model.userFullName.equals(s,true)){
                list.add(model)
            }
        }
        if(list.isEmpty()){
            binding.recyclerBilling.visibility= View.GONE

        }else{
            binding.recyclerBilling.visibility= View.VISIBLE
            setUpRec(list, pkgList1)
        }
    }

    private fun setUpRec(userList1: MutableList<UserModel>, pkgList1: MutableList<PackageDetails>) {
        val adapter= BillingAdapter(userList1, pkgList1, requireActivity())
        binding.recyclerBilling.layoutManager= LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
        binding.recyclerBilling.adapter= adapter
    }

    override fun onStarts() {

    }

    override fun onSuccess(
        userList: MutableList<UserModel>,
        packageList: MutableList<PackageDetails>
    ) {
        userList1.addAll(userList)
        pkgList1.addAll(packageList)
        setUpRec(userList,packageList)
    }

    override fun onError(e: String) {
      Toast.makeText(requireContext(), e, Toast.LENGTH_LONG).show()
    }


}