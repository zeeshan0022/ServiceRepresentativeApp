package com.joinhub.servicerepresentative.activities


import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.GoogleMap
import com.huawei.hms.location.FusedLocationProviderClient
import com.huawei.hms.location.LocationCallback
import com.huawei.hms.location.SettingsClient
import com.huawei.hms.maps.*
import com.huawei.hms.maps.model.*
import com.joinhub.complaintprotaluser.models.UserModel
import com.joinhub.khataapp.Utilites.ThemeDataStore.PreferencesKeys.name
import com.joinhub.servicerepresentative.R
import com.joinhub.servicerepresentative.WebServices.LoadUsers
import com.joinhub.servicerepresentative.databinding.ActivityComplaintInfoBinding
import com.joinhub.servicerepresentative.fragments.HomeFragment
import com.joinhub.servicerepresentative.interfaces.ComplaintStatusInterface
import com.joinhub.servicerepresentative.presenatator.ComplaintInfoPresentator
import com.joinhub.servicerepresentative.utitlies.Constants
import org.ksoap2.serialization.SoapObject


val type = arrayOf("Active", "Solved", "Rejected", "Working","Cancelled")
class ComplaintInfoActivity : AppCompatActivity() , OnMapReadyCallback, ComplaintStatusInterface, com.google.android.gms.maps.OnMapReadyCallback{
    private var mMarker: Marker? = null
    private lateinit var  locationManager: LocationManager
    private var latitude: Double = 0.0
    private var longitude = 0.0
    lateinit var  binding: ActivityComplaintInfoBinding
    lateinit var hMap:HuaweiMap
    private var pos:Int=0
    lateinit var status:String
    lateinit var  mGoogleFrag:com.google.android.gms.maps.SupportMapFragment
    private var mSupportMapFragment: SupportMapFragment? = null
    var lng:Double=0.0
    lateinit var presentator:ComplaintInfoPresentator
    var lat:Double=0.0

    private val REQUEST_LOCATION = 1
    companion object {
        private const val TAG = "MapViewDemoActivity"
       // private const val MAPVIEW_BUNDLE_KEY = "8C51E9232E6B7849D2F409D137922B9C3AF0EA6EDEB87861D255AA9AD0B1C44A"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapsInitializer.setApiKey("DAEDAOtE5kpDk9fqa1lz257gQmktfxO1LymWLbEwysqzW/rWoEzhNJxOhYZycFp2SRe/p1cJXpK1Hlh1Q5B1P9V51RgDY9jsfQLLgg==")
        binding= ActivityComplaintInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mSupportMapFragment = supportFragmentManager.findFragmentById(R.id.mapfragment_mapfragmentdemo) as SupportMapFragment?
        val b= intent.extras
        if(b!=null){
            pos= b.getInt("pos")
            setData()
            initDropDown()
        }
        presentator= ComplaintInfoPresentator(this,this)
        binding.btnGetDirection.setOnClickListener {
            if(checkGoogleGMS()){
                initMap()
            }else{
                getLocation()

            }
        }

        binding.statusDropDown.setOnItemClickListener { _, _, _, _ ->

            with (HomeFragment.list1[pos]){
                when {
                    binding.statusDropDown.text.toString() == "Working" -> {
                        status = "Working"
                        presentator.changeStatus(
                            complaintID,status
                        )
                    }
                    binding.statusDropDown.text.toString() == "Rejected" -> {
                        status = "Rejected"
                        presentator.changeStatus(
                            complaintID,status
                        )
                    }
                    binding.statusDropDown.text.toString() == "Solved" -> {
                        status = "Solved"
                        presentator.changeStatus(
                            complaintID,status
                        )
                    }
                }
            }
        }

        init()
        mGoogleFrag= supportFragmentManager.findFragmentById(R.id.map)
                as com.google.android.gms.maps.SupportMapFragment

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
                val strings = arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                // Request permissions.
                ActivityCompat.requestPermissions(this, strings, 1)
            }
        }
        if(checkGoogleGMS()){
            binding.huaweiLinear.visibility= View.GONE
            binding.googleMapLinear.visibility= View.VISIBLE
            val frag=supportFragmentManager.findFragmentById(R.id.map) as com.google.android.gms.maps.SupportMapFragment
            frag.getMapAsync(this)

        }else{

            binding.huaweiLinear.visibility= View.VISIBLE
            binding.googleMapLinear.visibility= View.GONE


            mSupportMapFragment?.getMapAsync(this)
           // setHuaweiMap()
        }

        binding.btnStartChat.setOnClickListener {
//            val intent= Intent(applicationContext,ChatDetailActivity::class.java)
//            intent.putExtra("userName", HomeFragment.list1[pos].u)
//            intent.putExtra("chatroom",chatRoomID)
//            intent.putExtra("name", HomeFragment.list1[pos].complaintName)
//            activity.startActivity(intent)
        }
    }

    private fun initMap() {

    }


    private fun setData() {

        with(HomeFragment.list1[pos]){
            binding.txtName.text= complaintName
            binding.txtIssue.text= complaintIssue
            binding.txtPhone.text=complaintPhone
            binding.txtDesc.text= complaintDesc
            lat= complaintLatn.toDouble()
            lng=complaintLong.toDouble()
            mSupportMapFragment?.getMapAsync(this@ComplaintInfoActivity)
        }
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
        hMap.isMyLocationEnabled=true
        
        hMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(lat,
                    lng
                ), 30f
            )
        )
        if (null != mMarker) {
            mMarker?.remove()
        }
        val options = MarkerOptions()
            .position(LatLng(lat, lng))
            .title("User Location")
            .snippet(HomeFragment.list1[pos].complaintName)
        mMarker = hMap.addMarker(options)
    }


    private fun initDropDown(){

        val adapter = ArrayAdapter(applicationContext,android.R.layout.simple_dropdown_item_1line,type)
        binding.statusDropDown.setAdapter(adapter)
        with(HomeFragment.list1[pos]){
            when (complaintStatus) {
                "Active" -> {
                    binding.statusDropDown.setText(binding.statusDropDown.adapter.getItem(0).toString(), false)
                }
                "Solved" -> {
                    binding.statusDropDown.setText(binding.statusDropDown.adapter.getItem(1).toString(), false)
                    binding.statusDropDown.isEnabled=false
                    binding.statusDropDown.dropDownHeight=0
                }
                "Rejected" -> {

                    binding.statusDropDown.dropDownHeight=0
                    binding.statusDropDown.isEnabled=false
                    binding.statusDropDown.setText(binding.statusDropDown.adapter.getItem(2).toString(), false)
                }
                "Working" -> {

                    binding.statusDropDown.setText(binding.statusDropDown.adapter.getItem(3).toString(), false)
                }
                "Cancelled" -> {
                    binding.statusDropDown.dropDownHeight=0
                    binding.statusDropDown.setText(binding.statusDropDown.adapter.getItem(4).toString(), false)
                    binding.statusDropDown.isEnabled=false
                }
            }


        }

    }
    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                applicationContext, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                applicationContext, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION
            )
        } else {
            val locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (locationGPS != null) {
                val lat = locationGPS.latitude
                val longi = locationGPS.longitude
                latitude = lat
                longitude = longi

            } else {
                Toast.makeText(applicationContext, "Unable to find location.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStarts() {

    }

    override fun onSuccess(messageString: String) {
        if(status=="Solved"){
            binding.statusDropDown.dropDownHeight=0
            binding.statusDropDown.setText(binding.statusDropDown.adapter.getItem(1).toString(), false)
            binding.statusDropDown.isEnabled=false
        }
    }

    override fun onError(e: String) {
    Toast.makeText(applicationContext,e,Toast.LENGTH_LONG).show()
    }

    override fun onMapReady(map: GoogleMap) {
        val latLn=com.google.android.gms.maps.model.LatLng(lat,lng)
        map.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(latLn, 200.0F))
    }


}