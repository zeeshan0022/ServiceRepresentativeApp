package com.joinhub.servicerepresentative.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner
import com.joinhub.alphavpn.utility.Preference
import com.joinhub.complaintprotaluser.models.PackageDetails
import com.joinhub.complaintprotaluser.models.UserModel
import com.joinhub.servicerepresentative.R
import com.joinhub.servicerepresentative.WebServices.CreateUser
import com.joinhub.servicerepresentative.WebServices.LoadPackageList
import com.joinhub.servicerepresentative.databinding.ActivityCreateUserBinding
import com.joinhub.servicerepresentative.utitlies.Constants.getPackageId
import org.ksoap2.serialization.SoapObject

class CreateUserActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCreateUserBinding
    private lateinit var  pkgList:MutableList<PackageDetails>
    private lateinit var spPackage:SmartMaterialSpinner<String>
    private lateinit var list:MutableList<String>
    lateinit var  preference:Preference
    private var pkgID:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCreateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pkgList= mutableListOf()
        preference= Preference(this)
        spPackage= findViewById(R.id.spinner1)
        list= mutableListOf()
        if(pkgList.isEmpty()) {
            loadPackages()
        }
        spPackage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
             pkgID=   getPackageId(pkgList, spPackage.selectedItem)

            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }
        binding.btnCreateUser.setOnClickListener {
            createUser(binding.fullNameEditText.text.toString(),binding.userIDeditText.text.toString(),binding.emailEditText.text.toString(),
                binding.phoneEditText.text.toString(),binding.CNICeditText.text.toString(),
                binding.addressEditText.text.toString())
        }
    }

    private fun createUser(name: String, userName: String, email: String, phone: String, cnic: String, address: String) {
        if(name==""|| userName=="" || email=="" || phone=="" || cnic=="" || address==""){
            if(name==""){
                binding.userNameLayout.error="Please Enter Name"
            }else{
                binding.userNameLayout.error=null
            }
            if(userName==""){
                binding.userIdLayout.error="Please Enter User Name"
            }else{
                binding.userIdLayout.error=null
            }
            if(email==""){
                binding.emailLayout.error="Please Enter Email"
            }else{
                binding.emailLayout.error=null
            }
            if(phone==""){
                binding.phoneLayout.error="Please Enter Phone Number"
            }else{
                binding.phoneLayout.error=null
            }
            if(cnic==""){
                binding.cnicLayout.error="Please Enter CNIC"
            }else{
                binding.cnicLayout.error=null
            }
            if(address==""){
                binding.addressLayout.error="Please Enter Address"
            }else{
                binding.addressLayout.error=null
            }
        }else{
            binding.addressLayout.error=null
            binding.cnicLayout.error=null
            binding.phoneLayout.error=null
            binding.emailLayout.error=null
            binding.userIdLayout.error=null
            binding.userNameLayout.error=null
            binding.progressBar.visibility= View.VISIBLE
            Thread{
                val api = CreateUser()
                val s = api.createUser(UserModel(1, userName, name,cnic,cnic, email, phone,address,pkgID,
                preference.getIntpreference("areaID")))
                runOnUiThread {
                    if(s=="true"){
                        binding.progressBar.visibility= View.GONE
                        Toast.makeText(applicationContext, "User Created Successfully", Toast.LENGTH_LONG).show()
                        finish()
                    }else{
                        binding.progressBar.visibility= View.GONE
                        Toast.makeText(applicationContext,
                            "Error in Data Insertion Reason: $s", Toast.LENGTH_LONG).show()
                    }
                }
            }.start()
        }
    }

    private fun loadPackages() {
        binding.progressBar.visibility= View.VISIBLE
        Thread{

            val api= LoadPackageList()
            val root= api.loadData(false)
          runOnUiThread {
                for ( index in 0 until root.propertyCount){
                    val childObj: SoapObject = root.getProperty(index) as SoapObject
                    pkgList.add(
                        PackageDetails(Integer.parseInt(childObj.getProperty("pkgID").toString()),
                            childObj.getPropertyAsString("pkgName"),
                            childObj.getPropertyAsString("pkgDesc"),
                            childObj.getPropertyAsString("pkgSpeed"),
                            childObj.getPropertyAsString("pkgVolume"),
                            childObj.getPropertyAsString("pkgRate").toDouble(),
                            childObj.getPropertyAsString("pkgBouns_Speed"),
                            childObj.getPropertyAsString("pkgVolume").toByteArray()
                        )
                    )
                }
                if(pkgList.isNotEmpty()){
                    setAdapter()
                }
                }

        }.start()
    }

    private fun setAdapter() {
        for(model in pkgList){
            list.add(model.pkgName)
        }
        spPackage.item= list
        binding.progressBar.visibility= View.GONE
    }

}