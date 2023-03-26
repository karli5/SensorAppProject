package viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import database.SensorData
import database.SensorDataRepository
import database.SensorDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Class to enable communication between fragments and database
class SensorDataViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SensorDataRepository

    // Storing sensor data list as LiveData
    val allSensorData: LiveData<List<SensorData>>

    // Initializing with data repository
    init {
        val sensorDataDao = SensorDatabase.getInstance(application).sensorDataDao()
        repository = SensorDataRepository(sensorDataDao)
        allSensorData = repository.allSensorData
    }

    // Carry an insert order with data to database
    fun insert(sensorData: SensorData) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(sensorData)
    }

    // Carry a delete all order to database
    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }
}
