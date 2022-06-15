package com.joinhub.servicerepresentative.WebServices

import com.joinhub.complaintprotaluser.models.UserModel
import com.joinhub.servicerepresentative.utitlies.Constants
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.PropertyInfo
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE
import java.lang.Exception

class CreateUser {
    private val SOAP_ACTION: String = "http://tempuri.org/createUser"
    private val OPERATION_NAME = "createUser"
    fun createUser(model: UserModel): String {
        val request = SoapObject(Constants.WSDL_TARGET_NAMESPACE, OPERATION_NAME)
        var pi = PropertyInfo()
        pi.setName("name")
        pi.value = model.userFullName
        pi.setType(String::class.java)
        request.addProperty(pi)
        //
        pi = PropertyInfo()
        pi.setName("userName")
        pi.value = model.userName
        pi.setType(String::class.java)
        request.addProperty(pi)
        //
        pi = PropertyInfo()
        pi.setName("email")
        pi.value = model.userEmail
        pi.setType(String::class.java)
        request.addProperty(pi)
        //
        pi = PropertyInfo()
        pi.setName("phone")
        pi.value = model.userPhone
        pi.setType(String::class.java)
        request.addProperty(pi)
        //

        pi = PropertyInfo()
        pi.setName("cnic")
        pi.value = model.userCNIC
        pi.setType(String::class.java)
        request.addProperty(pi)

        //
        pi = PropertyInfo()
        pi.setName("password")
        pi.value = model.userPass
        pi.setType(String::class.java)
        request.addProperty(pi)
        pi = PropertyInfo()
        pi.setName("pkgid")
        pi.value = model.pkgID
        pi.setType(Int::class.java)
        request.addProperty(pi)
        pi = PropertyInfo()
        pi.setName("areaId")
        pi.value = model.areaID
        pi.setType(Int::class.java)
        request.addProperty(pi)
        pi = PropertyInfo()
        pi.setName("address")
        pi.value = model.userAddress
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
        return response.toString()


    }
}