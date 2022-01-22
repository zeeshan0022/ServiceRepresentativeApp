package com.joinhub.servicerepresentative.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.joinhub.alphavpn.utility.Preference
import com.joinhub.complaintprotaluser.utilties.Constants
import com.joinhub.complaintprotaluser.viewmodels.ThemeViewModel
import com.joinhub.servicerepresentative.R
import com.joinhub.servicerepresentative.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var viewTheme: ThemeViewModel
    lateinit var binding: ActivityMainBinding
    companion object{
        var themeBool:Boolean = false
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

        val preference= Preference(baseContext)
        if(preference.isBooleenPreference("empID")){
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(baseContext,DashboardActivity::class.java))
                finish()
            },3000)
        }else{
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(baseContext,SigninActivity::class.java))
                finish()
            },3000)
        }
    }

    private fun init(){
        viewTheme= ViewModelProvider(this)[ThemeViewModel::class.java]
        checkTheme()
    }

    private fun checkTheme(){
        viewTheme.readFromDataStore.observe(this, {

            if(it.equals("1")){
                themeBool=false
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Constants.lightThemeStyle(this)
            }else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Constants.darkThemeStyle(this)
                themeBool=true
            }
        })
    }
}