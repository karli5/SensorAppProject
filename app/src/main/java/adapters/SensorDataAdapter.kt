package adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sensorappproject.R
import database.SensorData
import java.text.SimpleDateFormat
import java.util.*
// Adapter class to format and display sensor data in the list
class SensorDataAdapter internal constructor(context: Context) : RecyclerView.Adapter<SensorDataAdapter.SensorDataViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var sensorDataList = emptyList<SensorData>()

    inner class SensorDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sensorDataItemView: TextView = itemView.findViewById(R.id.sensor_data)
    }

    // Hold the sensor data view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorDataViewHolder {
        val itemView = inflater.inflate(R.layout.sensor_data_item, parent, false)
        return SensorDataViewHolder(itemView)
    }

    // Binding the sensor data to ViewHolder
    override fun onBindViewHolder(holder: SensorDataViewHolder, position: Int) {
        val sensorData = sensorDataList[position]

        // Formatting sensor values and timestamps
        val formattedTimestamp = formatTimestamp(sensorData.timestamp)
        val formattedSensorValues = formatSensorValues(sensorData.sensorValues)

        // Creating complete text for textView
        val sensorDataText = """
    Sensor: ${sensorData.sensorName}
    Timestamp: $formattedTimestamp
    $formattedSensorValues
    """.trimIndent()

        holder.sensorDataItemView.text = sensorDataText
    }

    // Return size of sensor data items
    override fun getItemCount() = sensorDataList.size

    // Setting the sensor data and notifying the adapter to update the view
    internal fun setSensorData(sensorData: List<SensorData>) {
        this.sensorDataList = sensorData
        notifyDataSetChanged()
    }

    // Format the given timestamp to a readable string
    fun formatTimestamp(timestamp: Long): String {
        val date = Date(timestamp)
        val dateFormat = SimpleDateFormat("dd.MM.yyyy, HH:mm:ss", Locale.getDefault())
        return dateFormat.format(date)
    }

    // Format the given sensor values based on the given dimension
    fun formatSensorValues(sensorValues: String): String {
        val sensorValuesList = sensorValues.split(",")
        return when (sensorValuesList.size) {
            1 -> "X: ${sensorValuesList[0]}"
            3 -> "X: ${sensorValuesList[0]}  Y: ${sensorValuesList[1]}  Z: ${sensorValuesList[2]}"
            else -> "N/A"
        }
    }


}