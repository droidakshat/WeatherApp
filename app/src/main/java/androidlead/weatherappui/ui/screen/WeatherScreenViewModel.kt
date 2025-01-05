package androidlead.weatherappui.ui.screen

import android.location.Location
import androidlead.weatherappui.data.WeatherRepository
import androidlead.weatherappui.di.AppCoroutineDispatchers
import androidlead.weatherappui.ui.screen.util.ForecastItem
import androidlead.weatherappui.util.LocationTracker
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherScreenViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val appCoroutineDispatchers: AppCoroutineDispatchers
) : ViewModel() {

    private val _forecastDays = MutableStateFlow<List<ForecastItem>>(emptyList())
    val forecastDays = _forecastDays.asStateFlow()

    var currentLocation by mutableStateOf<Location?>(null)

    fun getForecastForLocation() {
        viewModelScope.launch(appCoroutineDispatchers.io) {
            // Get the user's current location
            currentLocation = locationTracker.getCurrentLocation()

            // If location is retrieved, fetch the forecast
            currentLocation?.let { location ->
                val days = generateNext4Days()
                // Collect the forecast items and update state
                val roundedLatitude = String.format("%.4f", location.latitude).toDouble()
                val roundedLongitude = String.format("%.4f", location.longitude).toDouble()

                launch {

                    weatherRepository.getForecastItems(roundedLatitude, roundedLongitude, days)
                        .collect { forecastItems ->
                            _forecastDays.value = forecastItems
                        }
                }

                launch {
                    weatherRepository.refreshLocalForecastDays(location, roundedLatitude, roundedLongitude)
                }
            }
        }
    }

    private fun generateNext4Days(): List<String> {
        val dateFormatter = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        val calendar = java.util.Calendar.getInstance()

        // Get the current day
        val currentDay = dateFormatter.format(calendar.time)

        // Generate the next three days
        val nextDays = (1..6).map {
            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1)
            dateFormatter.format(calendar.time)
        }

        // Return a list with the current day at the beginning
        return listOf(currentDay) + nextDays
    }
}