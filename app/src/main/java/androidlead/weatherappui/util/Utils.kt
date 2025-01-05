package androidlead.weatherappui.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {
    const val DB_DATE_FORMAT = "yyyy-MM-dd"
    const val UI_DATE_FORMAT = "d MMM"
    const val DAY_OF_WEEK_FORMAT = "EEE"

    @SuppressLint("SimpleDateFormat")
    fun formatDate(date: Date, format: String): String {
        val formatter = java.text.SimpleDateFormat(format, java.util.Locale.getDefault())
        return formatter.format(date)
    }

    fun getDateFromString(dateStr: String, format: String): Date? {
        val formatter = java.text.SimpleDateFormat(format, java.util.Locale.getDefault())
        return formatter.parse(dateStr)
    }

    fun getCityFromCoordinates(context: Context, latitude: Double, longitude: Double): String? {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)

            if (!addresses.isNullOrEmpty()) {
                // Return the city name or null if it is unavailable
                addresses[0].locality ?: addresses[0].subAdminArea
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}