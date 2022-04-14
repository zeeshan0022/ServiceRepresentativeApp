package com.joinhub.servicerepresentative.utitlies

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.Window
import androidx.core.view.WindowInsetsControllerCompat
import com.joinhub.servicerepresentative.R
import java.text.SimpleDateFormat
import java.util.*


object Constants {

        const val WSDL_TARGET_NAMESPACE = "http://tempuri.org/"

        const val SOAP_ADDRESS = "http://192.168.0.102:2020/WebService1.asmx"


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
    }
