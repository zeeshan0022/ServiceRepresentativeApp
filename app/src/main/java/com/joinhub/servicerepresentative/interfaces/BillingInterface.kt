package com.joinhub.servicerepresentative.interfaces

import com.joinhub.complaintprotaluser.models.PackageDetails
import com.joinhub.complaintprotaluser.models.UserModel

interface BillingInterface {
    fun onStarts()
    fun onSuccess(userList:MutableList<UserModel>, packageList:MutableList<PackageDetails>)
    fun onError(e:String)
}