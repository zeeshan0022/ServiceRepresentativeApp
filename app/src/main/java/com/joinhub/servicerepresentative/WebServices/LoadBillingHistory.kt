package com.joinhub.servicerepresentative.WebServices

import com.joinhub.servicerepresentative.utitlies.Constants
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.PropertyInfo
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE

class LoadBillingHistory {
    val SOAP_ACTION: String = "http://tempuri.org/loadBillingSR"
    val OPERATION_NAME = "loadBillingSR"
    fun loadData(userName:String): SoapObject {

        val request = SoapObject(Constants.WSDL_TARGET_NAMESPACE, OPERATION_NAME)
        val pi = PropertyInfo()
        pi.setName("method")
        pi.value = userName
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
        val soap: SoapObject = response as SoapObject
        return  soap


    }
}