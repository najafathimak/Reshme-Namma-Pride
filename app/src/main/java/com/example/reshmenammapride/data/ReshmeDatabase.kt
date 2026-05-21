package com.example.reshmenammapride.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [SilkBatch::class, ClimateLog::class],
    version = 1,
    exportSchema = false
)
abstract class ReshmeDatabase : RoomDatabase() {
    abstract fun batchDao(): BatchDao
    abstract fun climateLogDao(): ClimateLogDao

    companion object {
        @Volatile
        private var INSTANCE: ReshmeDatabase? = null

        fun getDatabase(context: Context): ReshmeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ReshmeDatabase::class.java,
                    "reshme_namma_pride_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
