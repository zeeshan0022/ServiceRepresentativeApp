package com.joinhub.servicerepresentative.WebServices

import com.joinhub.complaintprotaluser.models.ManageApp
import com.joinhub.servicerepresentative.utitlies.Constants
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.PropertyInfo
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE

class ManageAppWebService {

    val SOAP_ACTION: String = "http://tempuri.org/ManageApp"
    val OPERATION_NAME = "ManageApp"
    fun loadData(model:ManageApp): String {


        val request = SoapObject(Constants.WSDL_TARGET_NAMESPACE, OPERATION_NAME)
        var pi = PropertyInfo()
        pi.setName("areaID")
        pi.value = model.areaID
        pi.setType(Int::class.java)
        request.addProperty(pi)

        pi = PropertyInfo()
        pi.setName("status")
        pi.value = model.status
        pi.setType(String::class.java)
        request.addProperty(pi)


        pi = PropertyInfo()
        pi.setName("date")
        pi.value = model.date
        pi.setType(String::class.java)
        request.addProperty(pi)

        pi = PropertyInfo()
        pi.setName("city")
        pi.value = model
            .city
        pi.setType(String::class.java)
        request.addProperty(pi)


        pi = PropertyInfo()
        pi.setName("start")
        pi.value = model.startFrom
        pi.setType(String::class.java)
        request.addProperty(pi)


        pi = PropertyInfo()
        pi.setName("end")
        pi.value = model.toEnd
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