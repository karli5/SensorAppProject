package database

import androidx.lifecycle.LiveData
import database.SensorData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

// Class for handling database operations with SensorDataDao
class SensorDataRepository(private val sensorDataDao: SensorDataDao) {

    val allSensorData: LiveData<List<SensorData>> = sensorDataDao.getAllSensorData()

    // Inserting a SensorData Object into database
    suspend fun insert(sensorData: SensorData) {
        withContext(Dispatchers.IO) {
            sensorDataDao.insert(sensorData)
        }
    }

    // Deleting a SensorData Object from the database
    suspend fun delete(sensorData: SensorData) {
        withContext(Dispatchers.IO) {
            sensorDataDao.delete(sensorData)
        }
    }

    // Deleting all SensorData Objects from the database
    suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            sensorDataDao.deleteAll()
        }
    }
}
