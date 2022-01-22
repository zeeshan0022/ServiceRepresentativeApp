package com.joinhub.servicerepresentative.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.StrictMode
import android.view.View
import android.widget.Toast
import com.joinhub.alphavpn.utility.Preference
import com.joinhub.complaintprotaluser.utilties.Constants
import com.joinhub.servicerepresentative.R
import com.joinhub.servicerepresentative.activities.MainActivity.Companion.themeBool
import com.joinhub.servicerepresentative.databinding.ActivitySigninBinding
import com.joinhub.servicerepresentative.interfaces.LoginInterface
import com.joinhub.servicerepresentative.presenatator.LoginPresentator

class SigninActivity : AppCompatActivity(),LoginInterface{
    lateinit var  binding: ActivitySigninBinding
    lateinit var presentator: LoginPresentator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        binding.btnlogin.setOnClickListener{
            checkData(binding.userIDeditText.text.toString().trim(),
                binding.passwordeditText.text.toString().trim())
        }
        binding.txtForgotPassword.setOnClickListener{
            startActivity(Intent(applicationContext, ForgotActivity::class.java))
        }
    }


    private fun checkData(id:String, password:String){
        if(id.isEmpty()|| password.isEmpty() ){
            if(id.isEmpty()){
                binding.userIdLayout.error= "Please Enter User ID"
            }else{

                binding.userIdLayout.error= null
            }

            if(password.isEmpty()){
                binding.passwordLayout.error= "Please Enter Password"
            }else{

                binding.passwordLayout.error= null
            }
        }else{
            binding.userIdLayout.error= null
            binding.passwordLayout.error= null
            binding.progressBar.visibility= View.VISIBLE

            presentator.LoginCredential(id,password)

        }
    }
    private fun init(){
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        presentator= LoginPresentator(this, baseContext,this)
        StrictMode.setThreadPolicy(policy)
        if(themeBool){
            Constants.darkThemeStyle(this)
        }else{
            Constants.lightThemeStyle(this)
        }
    }

    override fun onError(e: String) {
        binding.progressBar.visibility= View.GONE
        showToast(e)
    }

    override fun onSuccess() {
        binding.progressBar.visibility= View.GONE
        showToast("Login Successful")
        // Work in the UI thread
        val preference= Preference(baseContext)
        preference.setBooleanpreference("empID",true)
        preference.setStringpreference("emp_Id", binding.userIDeditText.text.toString())
        startActivity(Intent(baseContext,DashboardActivity::class.java))
        finish()
    }

    override fun onStarts() {
        binding.progressBar.visibility= View.VISIBLE
    }

    override fun showProgress() {   binding.progressBar.visibility= View.VISIBLE }

    override fun hideProgress() {
        binding.progressBar.visibility= View.GONE
    }

    private fun showToast(message :String){
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(baseContext, message, Toast.LENGTH_LONG).show()
        }
    }
}