package com.example.sensorappproject

import adapters.SensorDataAdapter
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import database.SensorData
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class SensorDataAdapterTest {

    private lateinit var context: Context
    private lateinit var adapter: SensorDataAdapter
    private lateinit var sensorDataList: List<SensorData>

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        adapter = SensorDataAdapter(context)
        sensorDataList = listOf(
            SensorData(1, 1617760861000L,  "Sensor 1", "1.0,2.0,3.0"),
            SensorData(2, 1617760872000L,  "Sensor 2", "4.0,5.0,6.0")
        )
        adapter.setSensorData(sensorDataList)
    }

    @Test
    fun getItemCount() {
        assertEquals(sensorDataList.size, adapter.itemCount)
    }

    @Test
    fun formatSensorValues() {
        val sensorValues1 = "1.0,2.0,3.0"
        val formattedSensorValues1 = "X: 1.0  Y: 2.0  Z: 3.0"
        assertEquals(formattedSensorValues1, adapter.formatSensorValues(sensorValues1))

        val sensorValues2 = "4.0"
        val formattedSensorValues2 = "X: 4.0"
        assertEquals(formattedSensorValues2, adapter.formatSensorValues(sensorValues2))

        val sensorValues3 = "7.0,8.0,9.0,10.0"
        val formattedSensorValues3 = "N/A"
        assertEquals(formattedSensorValues3, adapter.formatSensorValues(sensorValues3))
    }
}
