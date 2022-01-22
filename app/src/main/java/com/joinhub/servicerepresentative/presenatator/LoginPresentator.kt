package com.joinhub.servicerepresentative.presenatator

import android.content.Context
import android.os.Looper
import com.joinhub.servicerepresentative.WebServices.LoginApi
import com.joinhub.servicerepresentative.activities.SigninActivity
import com.joinhub.servicerepresentative.interfaces.LoginInterface


class LoginPresentator(var loginInterface: LoginInterface, var context: Context, var activity: SigninActivity) {


   fun LoginCredential(id:String, password:String){
       var s: String? = null;
       loginInterface.onStarts()
       Thread {
        Looper.prepare()
        val loginApi = LoginApi()
        try{
            s= loginApi.LoginUser(id,password)

        }catch (e :Exception){
            loginInterface.onError(e.message.toString())
        }
           activity.runOnUiThread {
               if(s == "true"){
                   loginInterface.onSuccess()
               }else{
                   loginInterface.onError(s!!)
               }
        }
      }.start()
    }

}