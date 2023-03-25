package database

import androidx.lifecycle.LiveData
import database.SensorData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SensorDataRepository(private val sensorDataDao: SensorDataDao) {

    val allSensorData: LiveData<List<SensorData>> = sensorDataDao.getAllSensorData()

    suspend fun insert(sensorData: SensorData) {
        withContext(Dispatchers.IO) {
            sensorDataDao.insert(sensorData)
        }
    }

    suspend fun delete(sensorData: SensorData) {
        withContext(Dispatchers.IO) {
            sensorDataDao.delete(sensorData)
        }
    }

    suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            sensorDataDao.deleteAll()
        }
    }
}
