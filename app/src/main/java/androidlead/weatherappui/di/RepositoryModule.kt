package androidlead.weatherappui.di

import androidlead.weatherappui.data.WeatherRepository
import androidlead.weatherappui.data.api.service.WeatherAPIService
import androidlead.weatherappui.data.db.WeatherAppDatabase
import androidlead.weatherappui.data.db.dao.ForecastDayDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideWeatherRepository(
        forecastDayDao: ForecastDayDao,
        weatherAPIService: WeatherAPIService,
        dispatchers: AppCoroutineDispatchers
    ): WeatherRepository {
        return WeatherRepository(
            forecastDayDao, weatherAPIService, dispatchers
        )
    }
}