package com.joinhub.servicerepresentative.presenatator

import android.app.Activity
import com.joinhub.servicerepresentative.WebServices.ChangeComplaintStatus
import com.joinhub.servicerepresentative.interfaces.ComplaintStatusInterface

class ComplaintInfoPresentator(val view:ComplaintStatusInterface, private val activity: Activity) {

    fun changeStatus(id:Int, status:String){
       view.onStarts()
        Thread{
            val api =ChangeComplaintStatus()
            val s = api.loadData(id, status)

            activity.runOnUiThread {
                if(s == "true"){
                    view.onSuccess("Saved")
                }else{
                    view.onError(s)
                }
            }
        }.start()
    }
}