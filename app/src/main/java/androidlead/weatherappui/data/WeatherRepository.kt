package androidlead.weatherappui.data

import android.location.Location
import androidlead.weatherappui.data.api.service.WeatherAPIService
import androidlead.weatherappui.data.db.dao.ForecastDayDao
import androidlead.weatherappui.data.db.model.ForecastDay
import androidlead.weatherappui.data.mappers.toForecastItem
import androidlead.weatherappui.di.AppCoroutineDispatchers
import androidlead.weatherappui.ui.screen.util.ForecastItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class WeatherRepository(
    private val forecastDayDao: ForecastDayDao,
    private val weatherAPIService: WeatherAPIService,
    private val dispatchers: AppCoroutineDispatchers
) {
    fun getForecastItems(latitude: Double, longitude: Double, days: List<String>): Flow<List<ForecastItem>> {
        return forecastDayDao.getForecastDays(latitude, longitude, days).map { it.map { it.toForecastItem() } }.flowOn(dispatchers.io)
    }

    suspend fun refreshLocalForecastDays(
        location: Location,
        roundedLatitude: Double,
        roundedLongitude: Double
    ) {
        withContext(dispatchers.io) {
            try {
                // Call the Weather API to get the forecast
                val response = weatherAPIService.getForecast(
                    latitude = location.latitude.toString(),
                    longitude = location.longitude.toString()
                )

                if (response.isSuccessful) {
                    val forecastResponse = response.body()
                    if (forecastResponse != null) {
                        // Extract data from the API response
                        val daily = forecastResponse.daily
                        val forecastDays = daily.time.mapIndexed { index, date ->
                            ForecastDay(
                                id = "${roundedLatitude}_${roundedLongitude}_$date",
                                longitude = roundedLongitude,
                                latitude = roundedLatitude,
                                temperature = (daily.temperature2mMax[index] + daily.temperature2mMin[index]) / 2,
                                date = date,
                                weatherCode = daily.weatherCode[index].toString()
                            )
                        }

                        // Save the data to the local database
                        forecastDayDao.insertForecastDays(forecastDays)
                    }
                }
            } catch (ignored: Exception) {
                // Log or handle any exceptions here (network issues, parsing errors, etc.)
                println("Failed to fetch or save forecast data: ${ignored.localizedMessage}")
            }
        }
    }
}