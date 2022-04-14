package com.joinhub.servicerepresentative.interfaces

interface LoadDataListInterface<E> {
    fun onStarts()
    fun onSuccess(list:MutableList<E>)
    fun onError(e:String)
}