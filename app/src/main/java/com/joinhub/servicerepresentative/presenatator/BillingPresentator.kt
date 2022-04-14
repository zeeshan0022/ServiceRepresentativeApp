package com.joinhub.servicerepresentative.presenatator

import android.app.Activity
import com.joinhub.complaintprotaluser.models.ComplaintModel
import com.joinhub.complaintprotaluser.models.PackageDetails
import com.joinhub.complaintprotaluser.models.UserModel
import com.joinhub.servicerepresentative.WebServices.LoadPackageList
import com.joinhub.servicerepresentative.WebServices.LoadUsers
import com.joinhub.servicerepresentative.interfaces.BillingInterface
import org.ksoap2.serialization.SoapObject

class BillingPresentator(val view:BillingInterface, val activity: Activity) {
    var userList:MutableList<UserModel> = mutableListOf()
    var pkgList:MutableList<PackageDetails> = mutableListOf()

    fun loadData(areaID:Int){
        view.onStarts()
        Thread{
            userList= mutableListOf()
            pkgList= mutableListOf()
                //
            val api=LoadUsers()
            val root= api.loadData(areaID)

            activity.runOnUiThread {
                for ( index in 0 until root.propertyCount){
                    val childObj: SoapObject = root.getProperty(index) as SoapObject
                    userList.add(
                        UserModel(Integer.parseInt(childObj.getProperty("userID").toString()),
                            childObj.getProperty("userName").toString(),
                            childObj.getProperty("userFullName").toString() ,
                            childObj.getProperty("userPass").toString(),
                            childObj.getProperty("userCNIC").toString(),
                            childObj.getProperty("userEmail").toString(),
                            childObj.getProperty("userPhone").toString(),
                            childObj.getProperty("userAddress").toString(),
                            Integer.parseInt(childObj.getProperty("pkgID").toString()),
                            Integer.parseInt(childObj.getProperty("areaID").toString()))
                    )
                }
                if(userList.isEmpty()){
                    view.onError("No User Found")
                }else{
                    getPkg()
                }
            }
        }.start()
    }

    private fun getPkg() {
        Thread{

            val api=LoadPackageList()
            val root= api.loadData(false)
            activity.runOnUiThread {
                for ( index in 0 until root.propertyCount){
                    val childObj: SoapObject = root.getProperty(index) as SoapObject
                    pkgList.add(
                        PackageDetails(Integer.parseInt(childObj.getProperty("pkgID").toString()),
                            childObj.getPropertyAsString("pkgName"),
                            childObj.getPropertyAsString("pkgDesc"),
                            childObj.getPropertyAsString("pkgSpeed"),
                            childObj.getPropertyAsString("pkgVolume"),
                            childObj.getPropertyAsString("pkgRate").toDouble(),
                            childObj.getPropertyAsString("pkgBouns_Speed"),
                            childObj.getPropertyAsString("pkgVolume").toByteArray()
                        )
                    )
                }
                if(pkgList.isEmpty()){
                    view.onError("No User Found")
                }else{
                   view.onSuccess(userList,pkgList)
                }
            }
        }.start()
    }
}