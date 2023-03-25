import androidx.lifecycle.LiveData
import androidx.room.*
import database.SensorData

@Dao
interface SensorDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sensorData: SensorData): Long

    @Query("SELECT * FROM sensor_data")
    fun getAllSensorData(): LiveData<List<SensorData>>

    @Delete
    fun delete(sensorData: SensorData): Int

    @Query("DELETE FROM sensor_data")
    fun deleteAll(): Int
}
