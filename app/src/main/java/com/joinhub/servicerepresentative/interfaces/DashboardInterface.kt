package com.joinhub.servicerepresentative.interfaces

interface DashboardInterface<E> {
    fun onStarts();
    fun onSuccess(list:MutableList<E>)
    fun onError(e:String)
}