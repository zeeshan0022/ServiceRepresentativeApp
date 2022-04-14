package com.joinhub.servicerepresentative.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joinhub.complaintprotaluser.models.PackageDetails
import com.joinhub.complaintprotaluser.models.UserModel
import com.joinhub.servicerepresentative.activities.UserBillingActivity
import com.joinhub.servicerepresentative.databinding.BillingListItemBinding

class BillingAdapter(private val userList:MutableList<UserModel>, val pkgList: MutableList<PackageDetails>, val activity: Activity):
    RecyclerView.Adapter<BillingAdapter.ViewHolder>() {
class ViewHolder(val binding:BillingListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(BillingListItemBinding.inflate(LayoutInflater.from(activity),
            parent,false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       with(holder.binding){
           with(userList[position]){
               txtName.text= userFullName
               txtUserName.text= userName
               txtStatus.text= userPhone
               txtCharges.text= "Rs:"+ getRate(pkgID)

               btnNext.setOnClickListener {
                   val intent= Intent(activity, UserBillingActivity::class.java)
                   intent.putExtra("data", userList[position])
                   intent.putExtra("charge", getRate(pkgID))
                   intent.putExtra("name", getName(pkgID))
                   activity.startActivity(intent)
               }
           }
       }
    }
    fun getRate(pkgid:Int):String{
        var rate =""
        for(model in pkgList){
            if(model.pkgID== pkgid){

               rate= model.pkgRate.toString()
                break
            }

        }
        return rate
    }
    fun getName(pkgid:Int):String{
        var rate =""
        for(model in pkgList){
            if(model.pkgID== pkgid){

                rate= model.pkgName
                break
            }

        }
        return rate
    }

    override fun getItemCount(): Int {
        return userList.size
    }


}