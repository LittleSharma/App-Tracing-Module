package com.example.apptrackingmodual.Utilities

object Constants {

    // AppTrackData for Room Database

    const val TABLE_NAME = "appTrace"
    const val KEY_ID = "id"
    const val KEY_APP_NAME = "appName"
    const val KEY_APP_PACKAGE_NAME = "appPackageName"
    const val KEY_APP_ICON = "appIcon"
    const val KEY_CURRENT_TIME_STAMP = "currentTimeStamp"
    const val KEY_APP_LAST_TIME_STAMP = "appLastTimeStamp"
    const val KEY_APP_FIRST_TIME_STAMP = "appFirstTimeStamp"
    const val KEY_APP_TOTAL_TIME_IN_FOREGROUND = "appTotalTimeInForeground"

    // Date & Time for Usage Stats Manager

    const val YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"
    const val YYYY_MM_DD = "yyyy-MM-dd"
    const val HH_MM_SS = "hh:mm:ss"

    // Notification Channel Id

    const val CHANNEL_ID = "AppTrack"

    // Shared Preference for Usage Stats Manager

    const val SharedprefernceName = "UserData"
    const val KEY_MOBILE_NUMBER = "mobile_no"
    const val KEY_PASSWORD = "password"
    const val KEY_DEVICE_ID = "device_id"
    const val FLAG_VALUE = "flag_value"
    const val CURRENT_TIME = "current_time"
    const val SYNC_DATA_STATUS = "sync_data_status"
    const val LOG_IN_TIME = "sync_data_status"
    const val LOG_OUT_TIME = "sync_data_status"

}