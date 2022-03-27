package com.joinhub.servicerepresentative.presenatator

import android.app.Activity
import com.joinhub.complaintprotaluser.models.ComplaintModel
import com.joinhub.complaintprotaluser.models.ServiceModel
import com.joinhub.complaintprotaluser.models.UserModel
import com.joinhub.servicerepresentative.WebServices.LoadComplaints
import com.joinhub.servicerepresentative.WebServices.LoadSRDetail
import com.joinhub.servicerepresentative.interfaces.DashboardInterface
import com.joinhub.servicerepresentative.interfaces.SingleDataInterface
import org.ksoap2.serialization.SoapObject
import javax.xml.parsers.ParserConfigurationException

class HomePresentator<E,M>(val interfaces: DashboardInterface<ComplaintModel>, val activity: Activity, val singalDataInterface :SingleDataInterface<M>) {
    lateinit var list:MutableList<ComplaintModel>
    fun loadComplaint(srID: Int){
        list= mutableListOf()
        interfaces.onStarts()
        Thread{
            val api= LoadComplaints()
            val root= api.loadData(srID)

            activity.runOnUiThread {
                for ( index in 0 until root.propertyCount){
                    val childObj: SoapObject = root.getProperty(index) as SoapObject
                    list.add(
                        ComplaintModel(Integer.parseInt(childObj.getProperty("complaintID").toString()),
                        childObj.getProperty("complaintTicketNo").toString(),childObj.getProperty("complaintName").toString() ,
                        childObj.getProperty("complaintPhone").toString(),childObj.getProperty("complaintEmail").toString(),
                        childObj.getProperty("complaintLong").toString(), childObj.getProperty("complaintLatn").toString(),
                        childObj.getProperty("complaintIssue").toString(),childObj.getProperty("complaintDesc").toString(),
                        childObj.getProperty("complaintStatus").toString(),Integer.parseInt(childObj.getProperty("serviceID").toString()),
                        Integer.parseInt(childObj.getProperty("userID").toString()), childObj.getProperty("date").toString())
                    )
                }
                if(list.isEmpty()){

                    interfaces.onError(root.toString())
                }else{

                    interfaces.onSuccess(list)
                }
            }
        }.start()
    }

    fun loadSRDetail(srID: String){
        Thread{
            val api=LoadSRDetail()
          val obj=  api.loadData(srID)
            activity.runOnUiThread {
                when {
                    Integer.parseInt(obj.getProperty("serviceID").toString())==0 -> {
                        interfaces.onError("Error in Loading data")
                    }
                    Integer.parseInt(obj.getProperty("serviceID").toString()) >0 -> {
                        try {

                            singalDataInterface.onSuccessModel(ServiceModel(
                                Integer.parseInt(obj.getProperty("serviceID").toString()),
                                obj.getProperty("serviceUserName").toString(),
                                obj.getProperty("serviceName").toString(),
                                obj.getProperty("serviceEmail").toString(),
                                obj.getProperty("servicePass").toString(),
                                obj.getProperty("serviceCNIC").toString(),
                                obj.getProperty("servicePhone").toString(),
                                Integer.parseInt(obj.getProperty("areaID").toString())
                            ))
                        } catch (e: ParserConfigurationException) {
                            e.printStackTrace()
                        }
                    }
                    else -> {
                       interfaces.onError(obj.toString())
                    }
                }
            }
        }.start()
    }
}