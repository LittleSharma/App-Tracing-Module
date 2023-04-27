package com.example.apptrackingmodual

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apptrackingmodual.Adapters.CustomAdapter
import com.example.apptrackingmodual.AppTrace.AppTraceDataDao
import com.example.apptrackingmodual.Controller.LogInActivity
import com.example.apptrackingmodual.Service.AppTrackingService
import com.example.apptrackingmodual.SharedPreference.AppTracePreference
import com.example.apptrackingmodual.Utilities.Constants
import com.example.apptrackingmodual.Utilities.Utilities
import com.example.apptrackingmodual.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var preference: AppTracePreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        Utilities.setUpPermissionUsageStats(this)

        preference = AppTracePreference()

        Log.d(
            "Your_Mobile_Number",
            preference.getStringValue(Constants.KEY_MOBILE_NUMBER, this).toString()
        )
        Log.d(
            "Your_Password",
            preference.getStringValue(Constants.KEY_PASSWORD, this).toString()
        )

        Log.d(
            "Your_Device_Id",
            preference.getStringValue(Constants.KEY_DEVICE_ID, this).toString()
        )

        Log.d(
            "Your_Flag_Value",
            preference.getBoolenValue(Constants.FLAG_VALUE, this).toString()
        )

        val intent: Intent = Intent(this, AppTrackingService::class.java)
        startService(intent)

        Utilities.getData(this, binding.appListRecycleView)

        Log.d("Your_Current_Date", Utilities.getCurrentDate().toString())
        Log.d("Your_Current_Date", Utilities.getLasTTenthDate(1).toString())
        Log.d("Your_Device_Id", Utilities.getDeviceId(this).toString())

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.new_game -> {
                Toast.makeText(this, "SignOut Successfully", Toast.LENGTH_LONG).show()
                preference.setSBoolenVale(Constants.FLAG_VALUE, false, this)
                startActivity(Intent(this@MainActivity, LogInActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

}