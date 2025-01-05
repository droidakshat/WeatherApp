package androidlead.weatherappui.data.api.service

import androidlead.weatherappui.data.api.model.GetForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPIService {
    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("daily") daily: String = "weather_code,temperature_2m_max,temperature_2m_min",
        @Query("timezone") timezone: String = "auto"
    ): Response<GetForecastResponse>
}