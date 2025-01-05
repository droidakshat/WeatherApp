package androidlead.weatherappui.ui.screen.util

import androidlead.weatherappui.R
import androidx.annotation.DrawableRes

data class ForecastItem(
    @DrawableRes val image: Int,
    val dayOfWeek: String,
    val date: String,
    val temperature: String,
    val weatherType: String,
)