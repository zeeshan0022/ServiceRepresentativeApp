package com.joinhub.complaintprotaluser.models

import android.os.Parcel
import android.os.Parcelable

data class UserModel(val userID:Int, val userName:String, val userFullName:String,
                     val userPass:String, val userCNIC:String, val userEmail:String,
                     val userPhone:String, val userAddress:String, val pkgID :Int, val areaID: Int):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(userID)
        parcel.writeString(userName)
        parcel.writeString(userFullName)
        parcel.writeString(userPass)
        parcel.writeString(userCNIC)
        parcel.writeString(userEmail)
        parcel.writeString(userPhone)
        parcel.writeString(userAddress)
        parcel.writeInt(pkgID)
        parcel.writeInt(areaID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }
}
