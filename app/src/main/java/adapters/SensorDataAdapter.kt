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

class SensorDataAdapter internal constructor(context: Context) : RecyclerView.Adapter<SensorDataAdapter.SensorDataViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var sensorDataList = emptyList<SensorData>()

    inner class SensorDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sensorDataItemView: TextView = itemView.findViewById(R.id.sensor_data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorDataViewHolder {

        val itemView = inflater.inflate(R.layout.sensor_data_item, parent, false)
        return SensorDataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SensorDataViewHolder, position: Int) {
        val sensorData = sensorDataList[position]

        // Zeitstempel formatieren
        val formattedTimestamp = formatTimestamp(sensorData.timestamp)

        // Sensorwerte formatieren
        val formattedSensorValues = formatSensorValues(sensorData.sensorValues)

        // Text für den TextView erstellen
        val sensorDataText = """
    Sensor: ${sensorData.sensorName}
    Timestamp: $formattedTimestamp
    $formattedSensorValues
    """.trimIndent()

        holder.sensorDataItemView.text = sensorDataText
    }


    override fun getItemCount() = sensorDataList.size

    internal fun setSensorData(sensorData: List<SensorData>) {
        this.sensorDataList = sensorData
        notifyDataSetChanged()
    }

    fun formatTimestamp(timestamp: Long): String {
        val date = Date(timestamp)
        val dateFormat = SimpleDateFormat("dd.MM.yyyy, HH:mm:ss", Locale.getDefault())
        return dateFormat.format(date)
    }

    fun formatSensorValues(sensorValues: String): String {
        val sensorValuesList = sensorValues.split(",")
        return when (sensorValuesList.size) {
            1 -> "X: ${sensorValuesList[0]}"
            3 -> "X: ${sensorValuesList[0]}  Y: ${sensorValuesList[1]}  Z: ${sensorValuesList[2]}"
            else -> "N/A"
        }
    }


}