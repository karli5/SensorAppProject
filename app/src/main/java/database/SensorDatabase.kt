import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import database.SensorData

@Database(entities = [SensorData::class], version = 1)
abstract class SensorDatabase : RoomDatabase() {

    abstract fun sensorDataDao(): SensorDataDao

    companion object {
        @Volatile
        private var INSTANCE: SensorDatabase? = null

        fun getInstance(context: Context): SensorDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SensorDatabase::class.java,
                    "sensor_data.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
