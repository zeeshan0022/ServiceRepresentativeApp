package com.joinhub.servicerepresentative.WebServices

import com.joinhub.servicerepresentative.utitlies.Constants.Companion.SOAP_ADDRESS
import com.joinhub.servicerepresentative.utitlies.Constants.Companion.WSDL_TARGET_NAMESPACE
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.PropertyInfo
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import java.lang.Exception

class LoginApi() {
    val SOAP_ACTION:String="http://tempuri.org/LoginEmp"
    val OPERATION_NAME = "LoginEmp"
    fun LoginUser(userID:String, password:String ):String{

        val request = SoapObject(WSDL_TARGET_NAMESPACE, OPERATION_NAME)
        var pi = PropertyInfo()
        pi.setName("id")
        pi.value = userID
        pi.setType(String::class.java)
        request.addProperty(pi)
        pi = PropertyInfo()
        pi.setName("pass")
        pi.value = password
        pi.setType(String::class.java)
        request.addProperty(pi)

        val envelope = SoapSerializationEnvelope(
            SoapEnvelope.VER11
        )
        envelope.dotNet = true

        envelope.setOutputSoapObject(request)

        val httpTransport = HttpTransportSE(SOAP_ADDRESS)
       val response: Any? = try {
            httpTransport.call(SOAP_ACTION, envelope)
            envelope.response
        } catch (exception: Exception) {
            exception.toString()
        }

        return  response.toString()



    }
}