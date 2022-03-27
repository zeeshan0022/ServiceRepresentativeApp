package com.joinhub.servicerepresentative.activities


import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.MapsInitializer
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.SupportMapFragment
import com.joinhub.servicerepresentative.R
import com.joinhub.servicerepresentative.databinding.ActivityComplaintInfoBinding
import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager

import android.os.Build
import android.view.View
import com.joinhub.servicerepresentative.utitlies.Constants


class ComplaintInfoActivity : AppCompatActivity() , OnMapReadyCallback{
    lateinit var  binding: ActivityComplaintInfoBinding
    lateinit var hMap:HuaweiMap
    lateinit var  mGoogleFrag:com.google.android.gms.maps.SupportMapFragment
    lateinit var mfrgmentHuawei: SupportMapFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapsInitializer.setApiKey("DAEDAGaEyT32GcbI3m2SmD6MIli6U0fFimXidQAWsdTCDMge7+XILbtgPmBZbQgqWljbYzGnvoiKycf4TeSwb2rbxlMLEDbUz+hlLQ==")
        binding= ActivityComplaintInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
     init()
        mfrgmentHuawei= supportFragmentManager.findFragmentById(R.id.mapHuawei) as SupportMapFragment
        mGoogleFrag= supportFragmentManager.findFragmentById(R.id.map) as com.google.android.gms.maps.SupportMapFragment
        if(checkGoogleGMS()){
            binding.huaweiLinear.visibility= View.GONE
            binding.googleMapLinear.visibility= View.VISIBLE
        }else{

            binding.huaweiLinear.visibility= View.VISIBLE
            binding.googleMapLinear.visibility= View.GONE
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                // Check whether your app has the specified permission and whether the app operation corresponding to the permission is allowed.
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // Request permissions for your app.
                    val strings = arrayOf<String>(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                    // Request permissions.
                    ActivityCompat.requestPermissions(this, strings, 1)
                }
            }
        }
        mfrgmentHuawei.getMapAsync(this)
    }

    private fun init(){
        if(MainActivity.themeBool){
            Constants.darkThemeStyle(this)
        }else{
            Constants.lightThemeStyle(this)
        }
    }
    private fun checkGoogleGMS():Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(this)
        if (status != ConnectionResult.SUCCESS) {

            return false
        }
        return true
    }

    override fun onMapReady(p0: HuaweiMap?) {
       hMap= p0!!
        hMap.isMyLocationEnabled= true
        hMap.uiSettings.isMyLocationButtonEnabled= true
    }
}