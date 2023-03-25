package com.example.sensorappproject

import database.SensorData
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class SensorDataUnitTest {

    @Test
    fun testFloatArrayToStringConversion() {
        // Eingabe: Ein FloatArray mit 3 Sensordaten
        val inputSensorData = floatArrayOf(1.0f, 2.0f, 3.0f)

        // Erwarteter Ausgabestring
        val expectedOutputString = "1.0,2.0,3.0"

        // Führen Sie die Konvertierung durch, indem Sie die Methode floatArrayToString aufrufen
        val outputString = floatArrayToString(inputSensorData)

        // Vergleichen Sie den resultierenden String mit dem erwarteten Wert
        assertEquals(expectedOutputString, outputString)
    }

    private fun floatArrayToString(sensorData: FloatArray): String {
        return sensorData.joinToString(separator = ",")
    }




}
