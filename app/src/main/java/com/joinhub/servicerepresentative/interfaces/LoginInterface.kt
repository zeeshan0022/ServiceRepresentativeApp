package com.joinhub.servicerepresentative.interfaces

interface LoginInterface {

    fun onError(e:String)
    fun onSuccess()
    fun onStarts()
    fun showProgress()
    fun hideProgress()
}