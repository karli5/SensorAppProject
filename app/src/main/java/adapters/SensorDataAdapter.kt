package adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sensorappproject.R
import database.SensorData

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
        val current = sensorDataList[position]
        holder.sensorDataItemView.text =
            "${current.sensorName}: ${current.sensorValues} (Timestamp: ${current.timestamp})"
    }

    override fun getItemCount() = sensorDataList.size

    internal fun setSensorData(sensorData: List<SensorData>) {
        this.sensorDataList = sensorData
        notifyDataSetChanged()
    }
}