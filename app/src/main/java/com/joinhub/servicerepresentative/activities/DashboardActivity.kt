package com.joinhub.servicerepresentative.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.joinhub.servicerepresentative.utitlies.Constants
import com.joinhub.servicerepresentative.R

import com.joinhub.servicerepresentative.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    companion object {
       @SuppressLint("StaticFieldLeak")
       lateinit var navController: NavController
       lateinit var textView:TextView
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        textView=binding.txtTitle
        val navView: BottomNavigationView = binding.navView
        binding.profileImage.setOnClickListener{
            startActivity(Intent(applicationContext,ProfileActivity::class.java))
        }
         navController = findNavController(R.id.nav_host_fragment_activity_dashboard)
        AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            ,R.id.navigation_chat)
        )
        navView.setupWithNavController(navController)
    }
    private fun init(){
        if(MainActivity.themeBool){
            Constants.darkThemeStyle(this)
        }else{
            Constants.lightThemeStyle(this)
        }
    }
}