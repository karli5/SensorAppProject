package database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sensor_data")
data class SensorData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val timestamp: Long,
    val sensorName: String,
    val sensorValues: String
)
