package androidlead.weatherappui.di

import android.content.Context
import androidlead.weatherappui.data.db.WeatherAppDatabase
import androidlead.weatherappui.data.db.dao.ForecastDayDao
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    private const val DATA_BASE_NAME = "weather_app_database.db"
    @Singleton
    @Provides
    fun provideRoomDatabase(@ApplicationContext context: Context): WeatherAppDatabase {
        return Room.databaseBuilder(
            context,
            WeatherAppDatabase::class.java,
            DATA_BASE_NAME,
        ).build()
    }

    @Singleton
    @Provides
    fun provideForecastDayDao(database: WeatherAppDatabase): ForecastDayDao {
        return database.forecastDayDao()
    }
}