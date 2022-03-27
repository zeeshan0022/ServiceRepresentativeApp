package com.joinhub.servicerepresentative.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.joinhub.alphavpn.utility.Preference
import com.joinhub.servicerepresentative.utitlies.Constants
import com.joinhub.servicerepresentative.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        binding.logout.setOnClickListener {
         val preference = Preference(applicationContext)
            preference.setBooleanpreference("empId",false)
            preference.setStringpreference("emp_Id",null)
            startActivity(Intent(applicationContext, SigninActivity::class.java))
            finish()
        }
    }

    fun init(){
        if(MainActivity.themeBool) {
        Constants.darkThemeStyle(this)
            binding.themeSwitch.isChecked= true
        }else{
            binding.themeSwitch.isChecked= false
            Constants.lightThemeStyle(this)
        }
    }
}