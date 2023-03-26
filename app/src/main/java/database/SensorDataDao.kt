package database

import androidx.lifecycle.LiveData
import androidx.room.*
import database.SensorData

// Interface for database operations on sensorData
@Dao
interface SensorDataDao {

    // Inserting a sensorData object into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sensorData: SensorData): Long

    // Getting all sensorData objects from the database
    @Query("SELECT * FROM sensor_data")
    fun getAllSensorData(): LiveData<List<SensorData>>

    // Deleting a specific sensorData object from the database
    @Delete
    fun delete(sensorData: SensorData): Int

    // Deleting all sensorData fron the database
    @Query("DELETE FROM sensor_data")
    fun deleteAll(): Int
}
