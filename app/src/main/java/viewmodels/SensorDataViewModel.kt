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

class SensorDataViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SensorDataRepository

    val allSensorData: LiveData<List<SensorData>>

    init {
        val sensorDataDao = SensorDatabase.getInstance(application).sensorDataDao()
        repository = SensorDataRepository(sensorDataDao)
        allSensorData = repository.allSensorData
    }

    fun insert(sensorData: SensorData) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(sensorData)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }
}
