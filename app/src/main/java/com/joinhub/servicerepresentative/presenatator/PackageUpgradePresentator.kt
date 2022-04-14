package com.joinhub.servicerepresentative.presenatator

import android.app.Activity
import com.joinhub.servicerepresentative.WebServices.UpgradePackageDetails

import com.joinhub.servicerepresentative.interfaces.PackageUpgradeInterface
import com.joinhub.servicerepresentative.utitlies.Constants

import java.util.*

class PackageUpgradePresentatorval(val interfaces: PackageUpgradeInterface, private val activity: Activity) {


    fun upgradePackage(userID:Int , pkgID:Int, method:String, charges:String, isUpgrade:Boolean, pkgName:String){
        interfaces.onStarts()
        val api = UpgradePackageDetails()
        Thread{
            val result= api.saveData(pkgID, method, Constants.getDate() ,"Paid", userID, charges,
            Constants.getMonth(), Constants.getYear(), isUpgrade,pkgName)

            activity.runOnUiThread {
                if(result=="true"){
                    interfaces.onSuccess("Payment Success. Please Restart Router")
                }else {
                    interfaces.onSuccess(result)
                }
            }
        }.start()

    }
}