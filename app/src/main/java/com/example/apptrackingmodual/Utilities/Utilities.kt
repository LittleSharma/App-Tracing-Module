package com.example.apptrackingmodual.Utilities

import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Process
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apptrackingmodual.Adapters.CustomAdapter
import com.example.apptrackingmodual.AppTrace.AppTraceDataDao
import java.io.ByteArrayOutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object Utilities {

    val TAG = "Check_Permission"
    val TAG1 = "Check_Data"
    lateinit var customAdapter: CustomAdapter

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

//        var stats_datarrr: ArrayList<AppTraceDataDao> = ArrayList()
//        val obj =AppTraceDataDao()
//        obj.appNameDao=stats.get(0).packageName
        var stats_data: String = ""
//        val appTraceDataDaoObj = AppTraceDataDao()



        for (i in 0..stats.size - 1) {

            stats_data = stats_data + "Package Name : " + stats[i].packageName +
                    "\n" + "App Name : " + getAppNameFromPkgName(context, stats[i].packageName) +
                    "\n" + "App Icon : " + getAppIconFromPkgName(context, stats[i].packageName) +
                    "\n" + "Last Time Used : " + convertMilliSecondsToDateFormatUTC(
                context, stats[i].lastTimeUsed, Constants.YYYY_MM_DD
            ) + "\n" + "Describe Contents : " + stats[i].describeContents() + "\n" + "First Time Stamp : " + convertMilliSecondsToDateFormatUTC(
                context, stats[i].firstTimeStamp, Constants.YYYY_MM_DD
            ) + "\n" + "Last Time Stamp : " + convertMilliSecondsToDateFormatUTC(
                context, stats[i].lastTimeStamp, Constants.YYYY_MM_DD
            ) + "\n" +
                    "Total Time in Foreground: " + convertMilliSecondsToDateFormatUTC(
                context,
                stats[i].totalTimeInForeground,
                Constants.HH_MM_SS
            ) + "\n\n"

        }

//        Log.d("Your_Class_Data_1", appTraceDataDaoObj.appIconDao.toString())

        tvUsageStats.layoutManager = LinearLayoutManager(context)
        customAdapter = CustomAdapter(stats,context)
        tvUsageStats.adapter = customAdapter

        Log.d(TAG1, stats_data.toString())

        Log.d(TAG1, stats.toString())
        Log.d(TAG1, stats.size.toString())
        Log.d(TAG1, stats[0].packageName)
        Log.d(TAG1, stats[0].firstTimeStamp.toString())
        Log.d(TAG1, stats[0].lastTimeStamp.toString())
        Log.d(TAG1, stats[0].lastTimeUsed.toString())
        Log.d(TAG1, stats[0].totalTimeInForeground.toString())

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

}