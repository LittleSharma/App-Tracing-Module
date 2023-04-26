package com.example.apptrackingmodual.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.apptrackingmodual.AppTrace.AppTrace
import com.example.apptrackingmodual.Dao.AppTraceDao

@Database(entities = [AppTrace::class], version = 1, exportSchema = false)
abstract class DatabaseHelper : RoomDatabase() {

    abstract fun appTraceDao() : AppTraceDao
    companion object {
        private var INSTANCE: DatabaseHelper? = null

        fun getDatabase(context: Context): DatabaseHelper {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseHelper::class.java,
                    "AppTraceDatabase"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}