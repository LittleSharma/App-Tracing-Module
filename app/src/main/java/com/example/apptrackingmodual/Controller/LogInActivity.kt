package com.example.apptrackingmodual.Controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.apptrackingmodual.MainActivity
import com.example.apptrackingmodual.R
import com.example.apptrackingmodual.SharedPreference.AppTracePreference
import com.example.apptrackingmodual.Utilities.Constants
import com.example.apptrackingmodual.Utilities.Utilities
import com.example.apptrackingmodual.databinding.ActivityLogInBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.prefs.Preferences

@OptIn(DelicateCoroutinesApi::class)
class LogInActivity : AppCompatActivity() {

    lateinit var binding: ActivityLogInBinding
    lateinit var preference: AppTracePreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_log_in)

        supportActionBar?.hide()
        preference = AppTracePreference()

        GlobalScope.launch {
            binding.signin.setOnClickListener {
                logIn()
            }
        }
    }

    fun logIn() {

        Utilities.show(this)

        if (binding.mobil.text.toString().isEmpty() || binding.spassword.text.toString()
                .isEmpty()
        ) {

            Toast.makeText(this, "Mobile/Password Require", Toast.LENGTH_LONG).show()
            Utilities.dismiss()
        } else {
            preference.setStringVale(
                Constants.KEY_MOBILE_NUMBER,
                binding.mobil.text.toString(),
                this
            )
            preference.setStringVale(
                Constants.KEY_PASSWORD,
                binding.spassword.text.toString(),
                this
            )
            preference.setStringVale(
                Constants.KEY_DEVICE_ID,
                Utilities.getDeviceId(this).toString(), this
            )
            preference.setSBoolenVale(Constants.FLAG_VALUE, true, this)
            Toast.makeText(this, "SignIn Successfully", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            Utilities.dismiss()
        }
    }


}