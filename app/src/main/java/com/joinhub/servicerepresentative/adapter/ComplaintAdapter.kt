package com.joinhub.servicerepresentative.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joinhub.complaintprotaluser.models.ComplaintModel
import com.joinhub.complaintprotaluser.models.ServiceModel
import com.joinhub.servicerepresentative.activities.ComplaintInfoActivity
import com.joinhub.servicerepresentative.databinding.ComplaintItemListBinding

class ComplaintAdapter(private val activity: Activity, private val list: MutableList<ComplaintModel>, val type:String):
    RecyclerView.Adapter<ComplaintAdapter.ComplaintViewHolder>() {
    class ComplaintViewHolder(val binding: ComplaintItemListBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComplaintViewHolder {

       return ComplaintViewHolder(ComplaintItemListBinding.inflate(LayoutInflater.from(activity),parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ComplaintViewHolder, position: Int) {
    with(holder.binding){
        with(list[position]){
                    txtTicket.text = "" + complaintTicketNo
                    txtCustomerName.text = complaintName
                    txtPhone.text = complaintPhone
                    txtStatus.text = complaintStatus
                    txtIssue.text = complaintIssue

                    moreInfo.setOnClickListener {
                        val i= Intent(activity,ComplaintInfoActivity::class.java)
                        i.putExtra("pos",position)
                        activity.startActivity(i)
                    }


        }
    }
    }

    override fun getItemCount(): Int {

        return if(type=="home"){
            2
        }else{
            list.size
        }
    }

}
