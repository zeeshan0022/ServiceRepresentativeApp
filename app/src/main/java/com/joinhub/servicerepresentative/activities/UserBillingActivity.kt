package com.joinhub.servicerepresentative.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.joinhub.complaintprotaluser.models.UserModel
import com.joinhub.servicerepresentative.R
import com.joinhub.servicerepresentative.databinding.ActivityUserBillingBinding
import com.joinhub.servicerepresentative.interfaces.PackageUpgradeInterface
import com.joinhub.servicerepresentative.presenatator.BillingPresentator
import com.joinhub.servicerepresentative.presenatator.PackageUpgradePresentatorval
import com.joinhub.servicerepresentative.utitlies.Constants

class UserBillingActivity : AppCompatActivity() , PackageUpgradeInterface{
    private lateinit var binding:ActivityUserBillingBinding
    lateinit var model:UserModel
    lateinit var charges:String
    lateinit var pkgName:String
    lateinit var presentator: PackageUpgradePresentatorval
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserBillingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val b= intent.extras
        if(b!=null){
            model= b.getParcelable<UserModel>("data")!!
            charges= b.getString("charge")!!
            pkgName= b.getString("name").toString()
            binding.txtUserName.text= "Customer Name:" + model.userFullName
            binding.txtName.text= "User Name"+ model.userName
            binding.txtEmail.text= "Email Address: ${model.userCNIC}"
            binding.txtPhone.text= "Phone No: ${model.userPhone}"
            binding.txtLastDate.text="Date: ${Constants.getDate()}"
            binding.txtLastCharges.text="Total Charges: Rs. $charges"

        }
        presentator= PackageUpgradePresentatorval(this, this)
        binding.btnRece.setOnClickListener {
            presentator.upgradePackage(model.userID,model.pkgID,"ByHand",charges,false,pkgName)
        }
    }

    override fun onStarts() {
    binding.progressBar.visibility= View.VISIBLE
    }

    override fun onSuccess(message: String) {
        binding.progressBar.visibility= View.GONE
        Toast.makeText(applicationContext,message,Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onError(e: String) {
        binding.progressBar.visibility= View.GONE
    }
}