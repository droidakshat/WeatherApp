package androidlead.weatherappui.data.api.model

import com.google.gson.annotations.SerializedName

data class GetForecastResponse(
    @SerializedName("daily")
    val daily: Daily,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
)

data class Daily(
    @SerializedName("temperature_2m_max")
    val temperature2mMax: List<Double>,

    @SerializedName("temperature_2m_min")
    val temperature2mMin: List<Double>,

    @SerializedName("time")
    val time: List<String>,

    @SerializedName("weather_code")
    val weatherCode: List<Int>
)