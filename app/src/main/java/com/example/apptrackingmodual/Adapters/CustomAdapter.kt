package com.example.apptrackingmodual.Adapters

import android.app.usage.UsageStats
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apptrackingmodual.AppTrace.AppTraceDataDao
import com.example.apptrackingmodual.R
import com.example.apptrackingmodual.Utilities.Constants
import com.example.apptrackingmodual.Utilities.Utilities

class CustomAdapter(
    private val mList: List<UsageStats>,
    private val context: Context
) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listlayout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        return holder.bindView(mList[position], context)

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var yourAppName : TextView = view.findViewById(R.id.app_name)
        var yourAppIcon : ImageView = view.findViewById(R.id.app_image)
        var appTotalTimeInForeground: TextView = view.findViewById(R.id.app_total_time_in_foreground)
        var appCurrentTimeStamp: TextView = view.findViewById(R.id.app_current_time_stamp)

        fun bindView(mList: UsageStats, context: Context) {

            yourAppName.text = Utilities.getAppNameFromPkgName(context, mList.packageName)
            appTotalTimeInForeground.text = Utilities.convertMilliSecondsToDateFormatUTC(context,mList.totalTimeInForeground,Constants.HH_MM_SS)
            yourAppIcon.setImageDrawable(Utilities.getAppIconFromPkgName(context,mList.packageName))
            appCurrentTimeStamp.text = Utilities.getCurrentDate()

        }

    }

}
