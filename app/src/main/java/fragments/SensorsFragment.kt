package fragments

import android.content.Context
import android.hardware.Sensor
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

class SensorsFragment : Fragment() {

    private var _binding: FragmentSensorsBinding? = null
    private val binding get() = _binding!!
    private lateinit var sensorManager: SensorManager
    private lateinit var sensorList: List<Sensor>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSensorsBinding.inflate(inflater, container, false)
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL)

        setupSensorSpinner()

        return binding.root
    }

    private fun setupSensorSpinner() {
        val sensorNames = sensorList.map { it.name }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sensorNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sensorSpinner.adapter = adapter

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
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.sensorInfo.text = ""
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

