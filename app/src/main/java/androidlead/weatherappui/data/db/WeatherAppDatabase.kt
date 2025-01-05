package androidlead.weatherappui.data.db

import androidlead.weatherappui.data.db.dao.ForecastDayDao
import androidlead.weatherappui.data.db.model.ForecastDay
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ForecastDay::class
    ],
    version = 1,
    exportSchema = true,
)
abstract class WeatherAppDatabase : RoomDatabase() {
    abstract fun forecastDayDao(): ForecastDayDao
}