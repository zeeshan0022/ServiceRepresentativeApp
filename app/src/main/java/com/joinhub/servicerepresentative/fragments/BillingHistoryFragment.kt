package com.joinhub.servicerepresentative.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joinhub.alphavpn.utility.Preference
import com.joinhub.complaintprotaluser.models.BillingModel
import com.joinhub.servicerepresentative.WebServices.LoadBillingHistory
import com.joinhub.servicerepresentative.adapter.BillingHistoryAdapter
import com.joinhub.servicerepresentative.databinding.FragmentBillingHistoryBinding
import com.joinhub.servicerepresentative.interfaces.LoadDataListInterface
import org.ksoap2.serialization.SoapObject


class BillingHistoryFragment : Fragment() , LoadDataListInterface<BillingModel>{
    lateinit var binding:FragmentBillingHistoryBinding
    lateinit var list: MutableList<BillingModel>
    lateinit var list1: MutableList<BillingModel>
    lateinit var adapter: BillingHistoryAdapter

    lateinit var  preference: Preference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentBillingHistoryBinding.inflate(inflater,container, false)
        list= mutableListOf()
        list1= mutableListOf()
        preference= Preference(requireContext())
        if(list.isEmpty()){
            binding.progressBar.visibility= View.VISIBLE
            loadHistory("ByHand by "+ preference.getStringpreference("serviceUserName",null)!!)
        }else{
            setUpRec(list )
        }
        binding.searchUser.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
               if(s!!.isBlank()){
                   setUpRec(list)
               }else{

               }
            }

        })
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

    private fun filterData(s: String) {
        list1.clear()
        for (model in list){
            if(model.userID.equals(s)){
                list1.add(model)
            }
        }

        if(list1.isEmpty()){
            binding.recBillingHistory.visibility= View.GONE

        }else{
            binding.recBillingHistory.visibility= View.VISIBLE
            setUpRec(list1)
        }
    }

    private fun loadHistory(userID: String){
        list= mutableListOf()
       val api= LoadBillingHistory()
        Thread{
            val root= api.loadData(userID)
            requireActivity().runOnUiThread {
                for ( index in 0 until root.propertyCount){
                    val childObj: SoapObject = root.getProperty(index) as SoapObject
                    list.add(
                        BillingModel(Integer.parseInt(childObj.getProperty("billingID").toString()),
                            Integer.parseInt(childObj.getProperty("userID").toString()),
                            childObj.getProperty("billingMethod").toString() ,
                            childObj.getProperty("billingDate").toString(),Integer.parseInt(childObj.getPropertyAsString("pkgID")),
                            childObj.getPropertyAsString("pkgName"),childObj.getPropertyAsString("charges").toDouble(),
                            childObj.getPropertyAsString("status"),childObj.getPropertyAsString("month"),childObj.getPropertyAsString("year")))
                }
                if(list.isEmpty()){
                    binding.progressBar.visibility= View.GONE
                    Toast.makeText(requireContext(),"No Data", Toast.LENGTH_LONG).show()
                }else{
                    binding.progressBar.visibility= View.GONE
                    setUpRec(list)

                }
            }
        }.start()
    }

    private fun setUpRec(list: MutableList<BillingModel>) {
        adapter= BillingHistoryAdapter(requireContext(),list)
        binding.recBillingHistory.layoutManager= LinearLayoutManager(requireContext(),
            RecyclerView.VERTICAL,false)
        binding.recBillingHistory.adapter= adapter
    }
}