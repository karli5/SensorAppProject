package fragments

import adapters.SensorDataAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sensorappproject.databinding.FragmentDataBinding
import viewmodels.SensorDataViewModel

class DataFragment : Fragment() {

    private var _binding: FragmentDataBinding? = null
    private val binding get() = _binding!!
    private lateinit var sensorDataViewModel: SensorDataViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sensorDataViewModel = ViewModelProvider(this).get(SensorDataViewModel::class.java)

        val recyclerView = binding.dataRecyclerView
        val adapter = SensorDataAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        sensorDataViewModel.allSensorData.observe(viewLifecycleOwner, Observer { sensorData ->
            sensorData?.let { adapter.setSensorData(it) }
        })

        binding.deleteAllButton.setOnClickListener {
            sensorDataViewModel.deleteAll()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
