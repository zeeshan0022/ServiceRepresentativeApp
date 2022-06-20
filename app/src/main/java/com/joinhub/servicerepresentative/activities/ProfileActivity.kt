package com.joinhub.servicerepresentative.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.joinhub.alphavpn.utility.Preference
import com.joinhub.complaintprotaluser.viewmodels.ThemeViewModel
import com.joinhub.servicerepresentative.ManageAppActivity
import com.joinhub.servicerepresentative.R
import com.joinhub.servicerepresentative.utitlies.Constants
import com.joinhub.servicerepresentative.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var  preference:Preference
    lateinit var viewTheme: ThemeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        binding.materialButton.setOnClickListener{
            startActivity(Intent(this, FullProfileActivity::class.java))
        }
        binding.materialCardView13.setOnClickListener{
            startActivity(Intent(this, FullProfileActivity::class.java))
        }
        binding.logout.setOnClickListener {

            preference.setBooleanpreference("user",false)
            startActivity(Intent(applicationContext, SigninActivity::class.java))
            finish()
        }
        binding.createUser.setOnClickListener {
            startActivity(Intent(applicationContext, CreateUserActivity::class.java))
        }
        binding.manageUser.setOnClickListener {
            startActivity(Intent(applicationContext, ManageAppActivity::class.java))
        }
        binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
              viewTheme.saveToDataStore(0)
              MainActivity.themeBool=true
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Constants.darkThemeStyle(this)
            }else{
                viewTheme.saveToDataStore(1)
                MainActivity.themeBool=false
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Constants.lightThemeStyle(this)
            }
        }
        if(preference.getStringpreference("imageV",null)!=null){
            binding.userImage.setImageBitmap(Constants.decodeBase64(preference.getStringpreference("imageV",null)))
        }else{
            binding.userImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.male_avatar))
        }
        binding.back.setOnClickListener { finish() }
        setData()
    }

    private fun setData() {
        binding.txtName.text=preference.getStringpreference("serviceName",null)
        binding.txtEmail.text=preference.getStringpreference("serviceEmail",null)
    }

    private fun init(){
        viewTheme= ViewModelProvider(this)[ThemeViewModel::class.java]
        preference = Preference(applicationContext)
        if(MainActivity.themeBool) {
        Constants.darkThemeStyle(this)
            binding.themeSwitch.isChecked= true
        }else{
            binding.themeSwitch.isChecked= false
            Constants.lightThemeStyle(this)
        }
    }
}