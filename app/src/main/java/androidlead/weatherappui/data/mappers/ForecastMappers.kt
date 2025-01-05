package androidlead.weatherappui.data.mappers

import androidlead.weatherappui.R
import androidlead.weatherappui.data.db.model.ForecastDay
import androidlead.weatherappui.ui.screen.util.ForecastItem
import androidlead.weatherappui.util.Utils
import java.util.Date

fun ForecastDay.toForecastItem(): ForecastItem {
    // Map the WMO weather code to the corresponding drawable and weather type
    val image = getImageFromWeatherCode(weatherCode)
    val weatherType = getWeatherTypeFromCode(weatherCode)

    // Convert the date to day of the week using SimpleDateFormat
    val dayOfWeek = getDayOfWeekFromDate(date)

    // Format temperature as a string with °C
    val temperatureFormatted = "${temperature.toInt()}"

    val dateObj = Utils.getDateFromString(date, Utils.DB_DATE_FORMAT)

    return ForecastItem(
        image = image,
        dayOfWeek = dayOfWeek,
        date = Utils.formatDate(dateObj?: Date(), Utils.UI_DATE_FORMAT),
        temperature = temperatureFormatted,
        weatherType = weatherType
    )
}

private fun getImageFromWeatherCode(weatherCode: String): Int {
    // Map WMO codes to drawable resources
    return when (weatherCode.toInt()) {
        in 1..3 -> R.drawable.img_sun  // Clear skies
        in 45..49 -> R.drawable.img_cloudy  // Foggy
        in 51..67 -> R.drawable.img_sub_rain  // Drizzle
        in 71..77 -> R.drawable.img_rain  // Snowfall
        in 80..82 -> R.drawable.img_rain  // Rain showers
        95 -> R.drawable.img_thunder  // Thunderstorms
        in 96..99 -> R.drawable.img_thunder  // Thunderstorms with hail
        else -> R.drawable.img_clouds  // Default to cloudy
    }
}

private fun getWeatherTypeFromCode(weatherCode: String): String {
    // Map WMO codes to weather type strings
    return when (weatherCode.toInt()) {
        in 1..3 -> "Clear Sky"
        in 45..49 -> "Foggy"
        in 51..67 -> "Drizzle"
        in 71..77 -> "Snowfall"
        in 80..82 -> "Rain Showers"
        95 -> "Thunderstorms"
        in 96..99 -> "Severe Thunderstorms"
        else -> "Cloudy"
    }
}

private fun getDayOfWeekFromDate(date: String): String {
    val dateObject = Utils.getDateFromString(date, Utils.DB_DATE_FORMAT)?: return ""

    return Utils.formatDate(dateObject, Utils.DAY_OF_WEEK_FORMAT)
}