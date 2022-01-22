package com.joinhub.servicerepresentative.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.joinhub.complaintprotaluser.utilties.Constants
import com.joinhub.servicerepresentative.R

class ForgotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)
    init()
    }

    private fun init(){
        if(MainActivity.themeBool){
            Constants.darkThemeStyle(this)
        }else{
            Constants.lightThemeStyle(this)
        }
    }
}