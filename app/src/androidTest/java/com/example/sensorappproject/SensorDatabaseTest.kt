package com.example.sensorappproject

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import database.SensorData
import database.SensorDataDao
import database.SensorDatabase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.TestCase.fail
import org.junit.Rule

class SensorDatabaseTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var sensorDataDao: SensorDataDao
    private lateinit var sensorDatabase: SensorDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        sensorDatabase = Room.inMemoryDatabaseBuilder(context, SensorDatabase::class.java).build()
        sensorDataDao = sensorDatabase.sensorDataDao()
    }

    @After
    fun tearDown() {
        sensorDatabase.close()
    }

    @Test
    fun insertAndGetSensorData() = runBlocking {
        val sensorData = SensorData(timestamp = System.currentTimeMillis(), sensorName = "Sensor 1", sensorValues = "1.0, 2.0, 3.0")
        val id = sensorDataDao.insert(sensorData)
        val sensorDataFromDb = sensorDataDao.getAllSensorData().getOrAwaitValue().find { it.id == id }
        assertEquals(sensorData.sensorName, sensorDataFromDb?.sensorName)
        assertEquals(sensorData.sensorValues, sensorDataFromDb?.sensorValues)
    }

    @Test
    fun deleteSensorData() = runBlocking {
        val sensorData = SensorData(timestamp = System.currentTimeMillis(), sensorName = "Sensor 1", sensorValues = "1.0, 2.0, 3.0")
        val id = sensorDataDao.insert(sensorData)
        val sensorDataFromDb = sensorDataDao.getAllSensorData().getOrAwaitValue().find { it.id == id }
        assertNotEquals(null, sensorDataFromDb)

        if (sensorDataFromDb != null) {
            val deleteResult = sensorDataDao.delete(sensorDataFromDb)
            assertEquals(1, deleteResult)
        } else {
            fail("No sensor data found in the database to delete.")
        }
    }


    @Test
    fun deleteAllSensorData() = runBlocking {
        val sensorData1 = SensorData(timestamp = System.currentTimeMillis(), sensorName = "Sensor 1", sensorValues = "1.0, 2.0, 3.0")
        val sensorData2 = SensorData(timestamp = System.currentTimeMillis(), sensorName = "Sensor 2", sensorValues = "4.0, 5.0, 6.0")
        sensorDataDao.insert(sensorData1)
        sensorDataDao.insert(sensorData2)

        val deleteResult = sensorDataDao.deleteAll()
        assertEquals(2, deleteResult)
    }




}


fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

    try {
        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }
    } finally {
        this.removeObserver(observer)
    }

    @Suppress("UNCHECKED_CAST")
    return data as T
}
