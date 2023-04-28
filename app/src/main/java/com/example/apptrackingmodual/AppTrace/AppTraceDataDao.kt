package com.example.apptrackingmodual.AppTrace

import android.graphics.drawable.Drawable


data class AppTraceDataDao(
    var appNameDao: String,
    var appIconDao: Drawable?,
    var totalTimeInForegroundDao: String,
    var currentTimeStampDao: String
) {

}