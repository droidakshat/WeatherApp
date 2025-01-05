package androidlead.weatherappui.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("forecast_days")
data class ForecastDay(
    @ColumnInfo("id")
    @PrimaryKey
    val id: String,
    @ColumnInfo("longitude")
    val longitude: Double,
    @ColumnInfo("latitude")
    val latitude: Double,
    @ColumnInfo("temperature")
    val temperature: Double,
    @ColumnInfo("date")
    val date: String,
    @ColumnInfo("weatherCode")
    val weatherCode: String
)
