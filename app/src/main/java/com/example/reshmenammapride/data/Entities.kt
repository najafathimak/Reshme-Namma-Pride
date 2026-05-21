package com.example.reshmenammapride.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "silkworm_batches")
data class SilkBatch(
    @PrimaryKey val id: String,
    val batchName: String,
    val breed: String,
    val startDate: String,
    val stage: String,
    val createdAt: Long
)

@Entity(
    tableName = "climate_logs",
    foreignKeys = [
        ForeignKey(
            entity = SilkBatch::class,
            parentColumns = ["id"],
            childColumns = ["batchId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["batchId"])]
)
data class ClimateLog(
    @PrimaryKey val id: String,
    val batchId: String,
    val batchName: String,
    val temperature: Float,
    val humidity: Float,
    val stage: String,
    val status: String,
    val advice: String,
    val loggedAt: Long
)
