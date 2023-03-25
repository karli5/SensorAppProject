import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import database.SensorData

@Dao
interface SensorDataDao {
    @Insert
    suspend fun insert(sensorData: SensorData)

    @Query("SELECT * FROM sensor_data")
    fun getAllSensorData(): LiveData<List<SensorData>>

    @Query("DELETE FROM sensor_data")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(sensorData: SensorData)
}
