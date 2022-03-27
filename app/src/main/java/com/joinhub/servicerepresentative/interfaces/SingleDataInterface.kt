package com.joinhub.servicerepresentative.interfaces

import com.joinhub.complaintprotaluser.models.ServiceModel

interface SingleDataInterface<E> {
    fun onSuccessModel(model: ServiceModel)

}