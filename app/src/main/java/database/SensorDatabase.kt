package database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import database.SensorData
// Representing Room database for sensor data
@Database(entities = [SensorData::class], version = 1, exportSchema = false)
abstract class SensorDatabase : RoomDatabase() {

    abstract fun sensorDataDao(): SensorDataDao

    companion object {
        @Volatile
        private var INSTANCE: SensorDatabase? = null

        // Returning an instance of the database, creating it if necessary
        fun getInstance(context: Context): SensorDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SensorDatabase::class.java,
                    "sensor_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
