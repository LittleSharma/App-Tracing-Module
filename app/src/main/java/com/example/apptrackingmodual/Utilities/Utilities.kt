package com.example.apptrackingmodual.Utilities

import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.app.Dialog
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Process
import android.provider.Settings
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apptrackingmodual.Adapters.CustomAdapter
import com.example.apptrackingmodual.AppTrace.AppTraceDataDao
import com.example.apptrackingmodual.R
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object Utilities {

    val TAG = "Check_Permission"
    val TAG1 = "Check_Data"

    @SuppressLint("StaticFieldLeak")
    lateinit var customAdapter: CustomAdapter
    lateinit var dialog: Dialog
    lateinit var arrayUsageData : ArrayList<AppTraceDataDao>

    fun setUpPermissionUsageStats(context: Context) {

        val appOps = context.getSystemService(AppCompatActivity.APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(),
            context.packageName
        )

        if (mode == AppOpsManager.MODE_ALLOWED) {
            Log.i(TAG, "Permission to record allowed")
        } else {
            Log.i(TAG, "Permission to record denied")
            val intent: Intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            startActivity(context, intent, null)
        }

    }

    fun getData(context: Context, tvUsageStats: RecyclerView) {

        val usageStatsManager =
            context.getSystemService(AppCompatActivity.USAGE_STATS_SERVICE) as UsageStatsManager
        val calendar: Calendar = Calendar.getInstance()
        calendar.add(Calendar.HOUR_OF_DAY, -1)
        val start: Long = calendar.getTimeInMillis()
        val end = System.currentTimeMillis()
        val stats: List<UsageStats> =
            usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, start, end)

        var stats_data: String = ""

        Log.d(
            "Your_Start_Time",
            convertLocalMillisecToUTCFormat(start, Constants.YYYY_MM_DD_HH_MM_SS)!!
        )
        Log.d(
            "Your_End_Time",
            convertLocalMillisecToUTCFormat(start, Constants.YYYY_MM_DD_HH_MM_SS)!!
        )


        for (i in 0..stats.size - 1) {

//            if (convertMilliSecondsToDateFormatUTC(context, stats[i].totalTimeInForeground, Constants.HH_MM_SS) != "12:00:00") {
//                arrayUsageList = ArrayList()
//                arrayUsageList[i].appNameDao= getAppNameFromPkgName(context, stats[i].packageName)
//                arrayUsageList[i].appIconDao = getAppIconFromPkgName(context, stats[i].packageName)
//                arrayUsageList[i].totalTimeInForegroundDao = convertMilliSecondsToDateFormatUTC(context, stats[i].totalTimeInForeground, Constants.HH_MM_SS)
//                arrayUsageList[i].currentTimeStampDao = getCurrentDate().toString()
//                Log.d("Your_App_Name", arrayUsageList[i].appNameDao)
//                Log.d("Your_App_Icon", arrayUsageList[i].appNameDao)
//                Log.d("Your_App_Total_Foreground_Time", arrayUsageList[i].appNameDao)
//                }
//            else {
//                Log.d("12:00:00 = ","12:00:00")
//            }

            stats_data = stats_data + "Package Name : " + stats[i].packageName +
                    "\n" + "App Name : " + getAppNameFromPkgName(context, stats[i].packageName) +
                    "\n" + "App Icon : " + getAppIconFromPkgName(context, stats[i].packageName) +
                    "\n" + "Last Time Used : " + convertMilliSecondsToDateFormatUTC(
                context, stats[i].lastTimeUsed, Constants.YYYY_MM_DD_HH_MM_SS
            ) + "\n" + "Describe Contents : " + stats[i].describeContents() + "\n" + "First Time Stamp : " + convertMilliSecondsToDateFormatUTC(
                context, stats[i].firstTimeStamp, Constants.YYYY_MM_DD_HH_MM_SS
            ) + "\n" + "Last Time Stamp : " + convertMilliSecondsToDateFormatUTC(
                context, stats[i].lastTimeStamp, Constants.YYYY_MM_DD_HH_MM_SS
            ) + "\n" +
                    "Total Time in Foreground: " + convertMilliSecondsToDateFormatUTC(
                context,
                stats[i].totalTimeInForeground,
                Constants.HH_MM_SS
            ) + "\n\n"

        }


        tvUsageStats.layoutManager = LinearLayoutManager(context)
        customAdapter = CustomAdapter(stats, context)
        tvUsageStats.adapter = customAdapter

        Log.d(TAG1, stats_data)
        Log.d(TAG1, stats.toString())
        Log.d(TAG1, stats.size.toString())

    }

    fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

    fun convertMilliSecondsToDateFormatUTC(
        context: Context,
        timeInMilliSeconds: Long,
        whichFormat: String?
    ): String {
        val sdf: SimpleDateFormat = SimpleDateFormat(whichFormat, Locale.ENGLISH)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.format(Date(timeInMilliSeconds))
    }

    fun getAppNameFromPkgName(context: Context, Packagename: String?): String {

        try {
            val packageManager = context.packageManager
            val info =
                packageManager.getApplicationInfo(Packagename!!, PackageManager.GET_META_DATA)
            val appName = packageManager.getApplicationLabel(info) as String
            return appName
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "n"

    }

    fun getAppIconFromPkgName(context: Context, Packagename: String?): Drawable {

        val packageManager = context.packageManager
        val info = packageManager.getApplicationInfo(Packagename!!, PackageManager.GET_META_DATA)
        val appIcon: Drawable = packageManager.getApplicationIcon(info.packageName)

        return appIcon
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDate(): String? {
        var currentDate: String? = null
        try {
            val c = Calendar.getInstance()
            val df = SimpleDateFormat(Constants.YYYY_MM_DD)
            currentDate = df.format(c.time)
            return currentDate
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return currentDate
    }

    @SuppressLint("SimpleDateFormat")
    fun getLasTTenthDate(idate: Int): String? {
        val dateFormat: DateFormat = SimpleDateFormat(Constants.YYYY_MM_DD)
        val date = Date()
        val todate = dateFormat.format(date)
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -idate * 10)
        val todate1 = cal.time
        return dateFormat.format(todate1)
    }

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context): String? {
        var deviceID: String? = ""
        deviceID = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        return deviceID
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(ParseException::class)
    fun getCurrentUtcTime(): String? {
        val df: DateFormat = SimpleDateFormat("hh:mm a")
        df.timeZone = TimeZone.getTimeZone("UTC")
        val gmtTime = df.format(Date())
        val cal = Calendar.getInstance()
        cal.time = df.parse(gmtTime)!! // all done
        cal.add(Calendar.MINUTE, 330)
        return df.format(cal.time)
    }

    fun convertLocalMillisecToUTCFormat(time: Long, format: String?): String? {
        var date = ""
        try {
            val sdf = SimpleDateFormat(format)
            sdf.timeZone = TimeZone.getTimeZone("UTC")
            date = sdf.format(Date(time))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return date
    }

    // Using Progress Bar Show

    fun show(context: Context) {
        dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.progressbar)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    // Using Progress Bar Dismiss

    fun dismiss() {
        if (dialog.isShowing)
            dialog.dismiss()
    }

    fun resetForegroundTime() {

    }


}