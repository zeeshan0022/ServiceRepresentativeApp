package com.joinhub.servicerepresentative.WebServices

import com.joinhub.servicerepresentative.utitlies.Constants
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.PropertyInfo
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE

class ChangeComplaintStatus {
    val SOAP_ACTION: String = "http://tempuri.org/changeComplaintStatusSR"
    val OPERATION_NAME = "changeComplaintStatusSR"
    fun loadData(id:Int, status:String): String {


        val request = SoapObject(Constants.WSDL_TARGET_NAMESPACE, OPERATION_NAME)
        var pi = PropertyInfo()
        pi.setName("complaintID")
        pi.value = id
        pi.setType(Int::class.java)
        request.addProperty(pi)

         pi = PropertyInfo()
        pi.setName("status")
        pi.value = status
        pi.setType(String::class.java)
        request.addProperty(pi)
        val envelope = SoapSerializationEnvelope(
            SoapEnvelope.VER11
        )
        envelope.dotNet = true

        envelope.setOutputSoapObject(request)

        val httpTransport = HttpTransportSE(Constants.SOAP_ADDRESS)
        val response: Any? = try {
            httpTransport.call(SOAP_ACTION, envelope)
            envelope.response
        } catch (exception: Exception) {
            exception.toString()
        }

        return  response.toString()


    }
}