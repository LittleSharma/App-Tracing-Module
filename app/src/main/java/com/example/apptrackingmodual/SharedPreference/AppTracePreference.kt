package com.example.apptrackingmodual.SharedPreference

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.example.apptrackingmodual.Utilities.Constants

class AppTracePreference {

    private var editor: SharedPreferences.Editor? = null
    private var preferences: SharedPreferences? = null

    // Using initialiseEditor Preference

    @SuppressLint("CommitPrefEdits")
    private fun initialiseEditor(cntx: Context) {
        preferences = cntx.getSharedPreferences(Constants.SharedprefernceName, Context.MODE_PRIVATE);
        editor = preferences!!.edit()

    }

    // Using commitEditor Preference

    private fun commitEditor() {
        editor!!.commit()
    }

    // Using setStringVale Preference

    @SuppressLint("CommitPrefEdits")
    fun setStringVale(key: String, value: String, cntx: Context) {
        initialiseEditor(cntx)

        editor!!.putString(key, value)

        commitEditor()

    }

    // Using setSBoolenVale Preference

    @SuppressLint("CommitPrefEdits")
    fun setSBoolenVale(key: String, value: Boolean, cntx: Context) {

        editor!!.putBoolean(key, value)

        commitEditor()
    }

    // Using getStringValue Preference

    fun getStringValue(key: String, cntx: Context): String? {
        initialiseEditor(cntx)

        return preferences?.getString(key, "null")
    }

    // Using getBoolenValue Preference

    fun getBoolenValue(key: String, cntx: Context): Boolean? {
        initialiseEditor(cntx)

        return preferences?.getBoolean(key, false)
    }

}