package com.joinhub.servicerepresentative.presenatator

import android.app.Activity
import com.joinhub.complaintprotaluser.models.BillingModel
import com.joinhub.servicerepresentative.interfaces.LoadDataListInterface

class BillingHistoryPresentator(val view:LoadDataListInterface<BillingModel>, val activity: Activity) {

    fun loadBilling(method:String){
        Thread{

            activity.runOnUiThread {
                
            }
        }.start()
    }
}