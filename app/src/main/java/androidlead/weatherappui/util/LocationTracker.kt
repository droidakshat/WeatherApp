package androidlead.weatherappui.util

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation(): Location?
}
