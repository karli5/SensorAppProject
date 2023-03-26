package fragments

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.sensorappproject.R
import com.example.sensorappproject.databinding.FragmentSensorsBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import database.SensorData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import viewmodels.SensorDataViewModel

// Class to choose a sensor from all avaiable sensors, recording sensor data and
// display more informations about the sensor
class SensorsFragment : Fragment(), SensorEventListener {

    private var _binding: FragmentSensorsBinding? = null
    private val binding get() = _binding!!
    private lateinit var sensorManager: SensorManager
    private lateinit var sensorList: List<Sensor>
    private var sensor: Sensor? = null
    private var isRecording = false
    private var sensorName: String = ""
    private lateinit var sensorDataViewModel: SensorDataViewModel
    private var currentSensorValues: FloatArray = floatArrayOf()

    // Inflate the layout of the fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSensorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Additional view set up
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Reading out the available sensors
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)
        sensorDataViewModel = ViewModelProvider(this).get(SensorDataViewModel::class.java)

        // Setting up the spinner element
        setupSensorSpinner()

        // Initializing the start button and defining switch conditions to stop
        binding.startButton.setOnClickListener {
            if (!isRecording) {
                isRecording = true
                binding.startButton.text = getString(R.string.sensor_stop)
                startRecording()
                sensor?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL) }
            } else {
                isRecording = false
                binding.startButton.text = getString(R.string.sensor_start)
                sensorManager.unregisterListener(this)
            }
        }
        // Save current Sensor
        savedInstanceState?.let {
            val selectedSensorIndex = it.getInt("selectedSensorIndex")
            binding.sensorSpinner.setSelection(selectedSensorIndex)
        }
    }

    // Setting up the spinner with the sensor names
    private fun setupSensorSpinner() {
        val sensorNames = sensorList.map { it.name }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sensorNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sensorSpinner.adapter = adapter

        // Formatting the sensor informations
        binding.sensorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val sensor = sensorList[position]
                val sensorInfo = """
                    Name: ${sensor.name}
                    Vendor: ${sensor.vendor}
                    Version: ${sensor.version}
                    Type: ${sensor.type}
                    Max Range: ${sensor.maximumRange}
                    Resolution: ${sensor.resolution}
                    Power: ${sensor.power} mA
                    """.trimIndent()
                binding.sensorInfo.text = sensorInfo
                this@SensorsFragment.sensor = sensorList[position]
                sensorName = sensor.name
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.sensorInfo.text = ""
            }
        }
    }


    // Handle sensor data updates
    override fun onSensorChanged(event: SensorEvent?) {
        val liveSensorValues = event?.values
        if (liveSensorValues != null) {
            currentSensorValues = liveSensorValues.clone()
            displayLiveSensorValues(liveSensorValues)
        }
    }

    // Not implemented
    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }

    // Unregister sensor listener when fragment is paused
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    // Register sensor listener when fragment is resumed
    override fun onResume() {
        super.onResume()
        if (isRecording) {
            sensor?.let { sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL) }
        }
    }

    // Cleaning up once fragment is destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Return the current sensor values
    private fun getSensorValues(): FloatArray {
        return currentSensorValues
    }

    // Displaying the formatted live sensor values
    private fun displayLiveSensorValues(sensorValues: FloatArray) {
        val liveSensorValuesText = when (sensorValues.size) {
            1 -> "X: ${sensorValues[0]}\nY: -\nZ: -"
            3 -> "X: ${sensorValues[0]}\nY: ${sensorValues[1]}\nZ: ${sensorValues[2]}"
            else -> "N/A"
        }
        binding.liveSensorValues.text = liveSensorValuesText
    }

    // Start recording the sensor data and saving every second one datapoint to the database
    private fun startRecording() {
        lifecycleScope.launch {
            while (isRecording) {
                val sensorValues = getSensorValues()
                val sensorValuesString = sensorValues.joinToString(separator = ",")
                val timestamp = System.currentTimeMillis()
                val sensorData = sensor?.let { SensorData(timestamp = timestamp, sensorName = it.name, sensorValues = sensorValuesString) }
                if (sensorData != null) {
                    sensorDataViewModel.insert(sensorData)
                }
                delay(1000)
            }
        }
    }

    // Save the state of the fragment in case the phone is e.g. turned to landscape orientation
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("selectedSensorIndex", binding.sensorSpinner.selectedItemPosition)
    }


}

