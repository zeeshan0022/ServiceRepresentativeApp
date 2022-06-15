package com.joinhub.servicerepresentative

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast
import com.joinhub.alphavpn.utility.Preference
import com.joinhub.complaintprotaluser.models.ManageApp
import com.joinhub.servicerepresentative.WebServices.ManageAppWebService
import com.joinhub.servicerepresentative.databinding.ActivityManageAppBinding
import com.joinhub.servicerepresentative.utitlies.Constants
import java.util.*

val list= arrayListOf(
    "Active","UnActive")
class ManageAppActivity : AppCompatActivity() {
    lateinit var binding:ActivityManageAppBinding
    lateinit var preference:Preference
    lateinit var status:String;
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityManageAppBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preference= Preference(this)
        val adapter= ArrayAdapter(this, android.R.layout.simple_list_item_1, list)
        binding.statusDropDown.setAdapter(adapter)
        binding.areaEditText.setText(""+preference.getIntpreference("areaID"))
        binding.cityeditText.setText(""+preference.getStringpreference("city"),null)
        binding.statusDropDown.setOnItemClickListener { _, _, _, _ ->
            status = if(binding.statusDropDown.text.toString()=="Active"){
                list[0]
            }else{
                list[1]
            }
         }
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
      //  binding.startFromeditText.ShowSoftInputOnFocus = false;
        binding.startFromeditText.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus){
                showDialogStart()
            }
        }
        binding.endDtaeeditText.setOnFocusChangeListener { _, hasFocus ->
            if(hasFocus){
                showDialogEnd()
            }
        }
        binding
            .addData.setOnClickListener {
                saveUserMaintainence()
            }
    }

    private fun saveUserMaintainence() {
        val model=ManageApp(0, preference.getIntpreference("areaID"),status,Constants.getDate(),
        binding.cityeditText.text.toString(), binding.startFromeditText.text.toString(),
        binding.endDtaeeditText.text.toString())

        Thread{
            val api= ManageAppWebService()
            val result= api.loadData(model)
            runOnUiThread {
                if(result=="true"){
                    Toast.makeText(applicationContext,"Notification is Sent Successfully",Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    Toast.makeText(applicationContext,"Error in Saving in Data",Toast.LENGTH_LONG).show()
                }
            }
        }.start()
    }

    @SuppressLint("SetTextI18n")
    private fun showDialogStart() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->

            // Display Selected date in textbox
            val month1= monthOfYear+1
            binding.startFromeditText.setText("$dayOfMonth/$month1/$year")

        }, year, month, day)

        dpd.show()
    }
    @SuppressLint("SetTextI18n")
    private fun showDialogEnd() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(this, { _, year, monthOfYear, dayOfMonth ->

            // Display Selected date in textbox
            val month1= monthOfYear+1
            binding.endDtaeeditText.setText("$dayOfMonth/$month1/$year")

        }, year, month, day)

        dpd.show()
    }
}