package com.joinhub.servicerepresentative.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.joinhub.servicerepresentative.utitlies.Constants
import com.joinhub.servicerepresentative.R
import com.joinhub.servicerepresentative.WebServices.ForgotPassword
import com.joinhub.servicerepresentative.databinding.ActivityForgotBinding

class ForgotActivity : AppCompatActivity() {
    lateinit var  binding:ActivityForgotBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityForgotBinding.inflate(layoutInflater)
        setContentView(binding.root)
    init()
        binding.imageView.setOnClickListener { finish() }
        binding.btnlogin.setOnClickListener {

            checkData(binding.userIDeditText.text.toString().trim(), binding.CNICeditText.text.toString().trim(),
                binding.NewPasseditText.text.toString().trim(), binding.ConfimPasseditText.text.toString().trim())
        }
    }
    private fun checkData(userID:String, cnic:String, newPass:String, confirmPassword:String) {
        if(userID==""|| cnic==""|| newPass==""|| confirmPassword==""){
            if(userID==""){
                binding.userIdLayout.error="Please Enter User Name"
            }else{
                binding.userIdLayout.error=null
            }
            if(cnic==""){
                binding.CNICLayout.error="Please Enter CNIC"
            }else{
                binding.CNICLayout.error=null
            }
            if(newPass==""){
                binding.NewPassLayout.error="Please Enter Password"
            }else{
                binding.NewPassLayout.error=null
            }
            if(confirmPassword==""){
                binding.ConfirmPassLayout.error="Please Enter Confirm Password"
            }else{
                binding.ConfirmPassLayout.error=null
            }
        }else {
            if (newPass == confirmPassword) {
                binding.ConfirmPassLayout.error=null
                binding.progressBar.visibility = View.VISIBLE

                Thread {
                    val api = ForgotPassword()
                    val result = api.saveData(userID, cnic, newPass, 0)
                    runOnUiThread {
                        if (result == "true") {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                applicationContext,
                                "Password Reset Successfully",
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        } else {
                            binding.progressBar.visibility = View.GONE
                            if (result == "false") {
                                Toast.makeText(
                                    applicationContext,
                                    "Incorrect UserName or CNIC",
                                    Toast.LENGTH_LONG
                                ).show()
                            } else {
                                Toast.makeText(applicationContext, result, Toast.LENGTH_LONG).show()

                            }

                        }
                    }
                }.start()
            }else{
                binding.ConfirmPassLayout.error="Password do not match"
            }
        }
    }
    private fun init(){
        if(MainActivity.themeBool){
            Constants.darkThemeStyle(this)
        }else{
            Constants.lightThemeStyle(this)
        }
    }
}