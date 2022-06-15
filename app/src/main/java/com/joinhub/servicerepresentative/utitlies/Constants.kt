package com.joinhub.servicerepresentative.utitlies

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import android.view.View
import android.view.Window
import androidx.core.view.WindowInsetsControllerCompat
import com.joinhub.complaintprotaluser.models.PackageDetails
import com.joinhub.servicerepresentative.R
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*


object Constants {
    val ARG_USERS = "users"
    val ARG_RECEIVER = "receiver"
    val ARG_RECEIVER_UID = "receiver_uid"
    val ARG_CHAT_ROOMS = "chat_rooms"
    val ARG_FIREBASE_TOKEN = "firebaseToken"
    val ARG_FRIENDS = "friends"
    val ARG_UID = "uid"

        const val WSDL_TARGET_NAMESPACE = "http://tempuri.org/"

        const val SOAP_ADDRESS = "http://192.168.0.103:2020/WebService1.asmx"


        fun darkThemeStyle(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val window: Window = activity.window
                val decorView: View = window.decorView
                val wic = WindowInsetsControllerCompat(window, decorView)
                wic.isAppearanceLightStatusBars = false // true or false as desired.
                window.statusBarColor = activity.getColor(R.color.backDark)
                window.navigationBarColor = activity.getColor(R.color.backDark)

            }
        }

    @SuppressLint("SimpleDateFormat")
    fun getDate(timestamp: Long): String {
        val stamp = Timestamp(timestamp) // from java.sql.timestamp
        val date = Date(stamp.time)

        val sdf = SimpleDateFormat("yyyy/MMM/dd")
        sdf.timeZone = TimeZone.getTimeZone("Asia/Karachi") // set your timezone appropriately

        return sdf.format(date)
    }

    fun lightThemeStyle(activity: Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val window: Window = activity.window
                val decorView: View = window.decorView
                val wic = WindowInsetsControllerCompat(window, decorView)
                wic.isAppearanceLightStatusBars = true // true or false as desired.

                // And then you can set any background color to the status bar.
                window.statusBarColor = activity.getColor(R.color.back)
                window.navigationBarColor = activity.getColor(R.color.back)
            }
        }


    fun getDate(): String {
        val c = Calendar.getInstance().time
        println("Current time => $c")

        val df = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return df.format(c)
    }
    fun getYear():String{
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        return  year.toString()
    }
    fun getMonth():String{
        val calendar: Calendar = Calendar.getInstance()
        val i= calendar.get(Calendar.MONTH)+1
        return i.toString()

    }
    fun getPackageId(list:MutableList<PackageDetails>, pkgName:String):Int{
        for(model in list){
            if(pkgName==model.pkgName){
                return model.pkgID
            }
        }
        return -1
    }
    }
