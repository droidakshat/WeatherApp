package androidlead.weatherappui.data.db.dao

import androidlead.weatherappui.data.db.model.ForecastDay
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecastDays(forecastDays: List<ForecastDay>)

    @Query("DELETE FROM forecast_days")
    suspend fun clearAllData()

    @Query("SELECT * FROM forecast_days WHERE latitude = :latitude AND longitude = :longitude AND date IN (:days)")
    fun getForecastDays(latitude: Double, longitude: Double, days: List<String>): Flow<List<ForecastDay>>
}