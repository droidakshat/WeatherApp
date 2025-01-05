package androidlead.weatherappui.ui.screen

import android.location.Location
import androidlead.weatherappui.data.db.model.ForecastDay
import androidlead.weatherappui.ui.screen.components.ActionBar
import androidlead.weatherappui.ui.screen.components.AirQuality
import androidlead.weatherappui.ui.screen.components.DailyForecast
import androidlead.weatherappui.ui.screen.components.WeeklyForecast
import androidlead.weatherappui.ui.screen.util.ForecastItem
import androidlead.weatherappui.ui.theme.ColorBackground
import androidlead.weatherappui.util.Utils
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun WeatherScreen(viewModel: WeatherScreenViewModel = viewModel()) {
    val forecastDays by viewModel.forecastDays.collectAsStateWithLifecycle()
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ), onPermissionsResult = {

        }
    )
    LaunchedEffect(true) {
        locationPermissions.launchMultiplePermissionRequest()
    }
    LaunchedEffect(locationPermissions.allPermissionsGranted) {
        if (locationPermissions.allPermissionsGranted) {
            viewModel.getForecastForLocation()
        }
    }
    WeatherScreenContent(days = forecastDays, location = viewModel.currentLocation)
}

@Composable
private fun WeatherScreenContent(days: List<ForecastItem>, location: Location?) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ColorBackground
    ) { paddings ->
        if (days.size >= 7 && location != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddings)
                    .padding(horizontal = 24.dp, vertical = 10.dp)
            ) {
                ActionBar(location = Utils.getCityFromCoordinates(LocalContext.current, location.latitude, location.longitude)?: "")
                Spacer(
                    modifier = Modifier.height(12.dp)
                )
                DailyForecast(forecastDay = days[0])
                Spacer(
                    modifier = Modifier.height(24.dp)
                )
                WeeklyForecast(data = days.drop(1))
            }
        } else {
           //TODO: Add loading screen here
        }
    }
}