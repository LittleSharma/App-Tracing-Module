package com.example.apptrackingmodual.Dao

import androidx.room.*
import com.example.apptrackingmodual.AppTrace.AppTrace

@Dao
interface AppTraceDao {

    @Query("SELECT * FROM appTrace")
    fun getAllUser() : List<AppTrace>

    @Insert
    fun insertAll (appTrace: AppTrace)

    @Update
    fun updateUsers(appTrace: AppTrace)

    @Delete
    fun deleteAll (appTrace: AppTrace)

}