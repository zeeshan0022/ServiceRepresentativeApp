package com.joinhub.servicerepresentative.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joinhub.alphavpn.utility.Preference
import com.joinhub.complaintprotaluser.models.ComplaintModel
import com.joinhub.complaintprotaluser.models.ServiceModel
import com.joinhub.servicerepresentative.R
import com.joinhub.servicerepresentative.activities.DashboardActivity
import com.joinhub.servicerepresentative.adapter.ComplaintAdapter
import com.joinhub.servicerepresentative.databinding.FragmentHomeBinding
import com.joinhub.servicerepresentative.interfaces.DashboardInterface
import com.joinhub.servicerepresentative.interfaces.SingleDataInterface
import com.joinhub.servicerepresentative.presenatator.HomePresentator

class HomeFragment : Fragment(), DashboardInterface<ComplaintModel>,SingleDataInterface<ServiceModel> {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenatator:HomePresentator<ComplaintModel,ServiceModel>
    lateinit var preference: Preference
    lateinit var adapter:ComplaintAdapter
    companion object{
        var list1:MutableList<ComplaintModel> = mutableListOf()
    }

    private var activeComplaint:Int=0
    private var solvedComplaint:Int=0
    private var allComplaint:Int=0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        preference= Preference(requireContext())
        presenatator= HomePresentator(this,requireActivity(),this)
        init()
        binding.txtViewAllComplaint.setOnClickListener{
           DashboardActivity.navController.navigate(R.id.navigation_dashboard)
        }
        if(preference.getIntpreference("serviceID")==0){

            presenatator.loadSRDetail(preference.getStringpreference("serviceUserName",null).toString())
        }else{
            if(list1.isEmpty()){
                Toast.makeText(requireContext(),preference.getIntpreference("serviceID").toString(),Toast.LENGTH_LONG).show()
                presenatator.loadComplaint(preference.getIntpreference("serviceID"))
            }else{
                calculateData(list1)
                setUpRece(list1)
            }
        }
        return binding.root
    }

    private fun setUpRece(list1: MutableList<ComplaintModel>) {
        adapter= ComplaintAdapter(requireActivity(),list1, "home")
        binding.recyclerHomeComplaint.layoutManager= LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
        binding.recyclerHomeComplaint.adapter= adapter
        binding.complaintPro.visibility= View.GONE
    }

    private fun init() {

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

    override fun onStarts() {

    }

    override fun onError(e: String) {

    }

    override fun onSuccessModel(model: ServiceModel) {
        preference.setIntpreference("serviceID",model.serviceID)
        preference.setStringpreference("serviceName",model.serviceName)
        preference.setStringpreference("serviceEmail",model.serviceEmail)
        preference.setStringpreference("servicePass",model.servicePass)
        preference.setStringpreference("serviceCNIC",model.serviceCNIC)
        preference.setStringpreference("servicePhone",model.servicePhone)
        preference.setIntpreference("areaID",model.areaID)
        Toast.makeText(requireContext(),"SR Sucess",Toast.LENGTH_LONG).show()
        presenatator.loadComplaint(model.serviceID)


    }

    override fun onSuccess(list: MutableList<ComplaintModel>) {
        list1.addAll(list)
        calculateData(list)
        setUpRece(list)
    }

    @SuppressLint("SetTextI18n")
    fun calculateData(listCal:MutableList<ComplaintModel>){
        activeComplaint=0
        allComplaint=0
        solvedComplaint=0
        for(model in listCal){
            when (model.complaintStatus) {
                "Active" -> {
                    activeComplaint++
                }
                "Solved" -> {
                    solvedComplaint++
                }
                "Cancelled" -> {
                    allComplaint++
                }
            }
        }
        binding.pro1.visibility= View.GONE
        binding.pro2.visibility= View.GONE
        binding.pro3.visibility= View.GONE
        binding.txtActive.text= ""+ activeComplaint
        binding.txtSolved.text=""+ solvedComplaint
        binding.txtPending.text=""+ allComplaint

    }
}