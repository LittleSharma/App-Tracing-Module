package com.example.apptrackingmodual.AppTrace

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.apptrackingmodual.Utilities.Constants

@Entity(tableName = Constants.TABLE_NAME)
class AppTrace (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.KEY_ID)
    var id: Int,

    @ColumnInfo(name = Constants.KEY_APP_NAME)
    var appName: String = "",

    @ColumnInfo(name = Constants.KEY_APP_PACKAGE_NAME)
    var appPackageName: String = "",

    @ColumnInfo(name = Constants.KEY_APP_ICON)
    var appIcon: String = "",

    @ColumnInfo(name = Constants.KEY_CURRENT_TIME_STAMP)
    var currentTimeStamp: String = "",

    @ColumnInfo(name = Constants.KEY_APP_LAST_TIME_STAMP)
    var appLastTimeStamp: String = "",

    @ColumnInfo(name = Constants.KEY_APP_FIRST_TIME_STAMP)
    var appFirstTimeStamp: String = "",

    @ColumnInfo(name = Constants.KEY_APP_TOTAL_TIME_IN_FOREGROUND)
    var appTotalTimeInForeground: String = "",

)