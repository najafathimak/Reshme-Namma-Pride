package com.example.reshmenammapride.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BatchDao {
    @Query("SELECT * FROM silkworm_batches ORDER BY createdAt DESC")
    fun observeBatches(): Flow<List<SilkBatch>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBatch(batch: SilkBatch)

    @Delete
    suspend fun deleteBatch(batch: SilkBatch)
}

@Dao
interface ClimateLogDao {
    @Query("SELECT * FROM climate_logs ORDER BY loggedAt DESC")
    fun observeLogs(): Flow<List<ClimateLog>>

    @Query("SELECT * FROM climate_logs WHERE batchId = :batchId ORDER BY loggedAt DESC")
    fun observeLogsForBatch(batchId: String): Flow<List<ClimateLog>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: ClimateLog)

    @Delete
    suspend fun deleteLog(log: ClimateLog)
}
