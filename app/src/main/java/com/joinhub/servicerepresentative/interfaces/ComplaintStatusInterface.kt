package com.joinhub.servicerepresentative.interfaces

interface ComplaintStatusInterface {
    fun onStarts()
    fun onSuccess(messageString: String)
    fun onError(e:String)
}