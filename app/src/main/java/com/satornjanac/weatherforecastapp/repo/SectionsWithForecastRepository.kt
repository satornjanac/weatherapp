package com.satornjanac.weatherforecastapp.repo

import com.satornjanac.weatherforecastapp.di.IoDispatcher
import com.satornjanac.weatherforecastapp.model.Forecast
import com.satornjanac.weatherforecastapp.model.Section
import com.satornjanac.weatherforecastapp.model.SectionTypes
import com.satornjanac.weatherforecastapp.model.Sections
import com.satornjanac.weatherforecastapp.model.ui.DisplayItems
import com.satornjanac.weatherforecastapp.networking.core.ApiResult
import com.satornjanac.weatherforecastapp.networking.core.Error
import com.satornjanac.weatherforecastapp.networking.api.ForecastApi
import com.satornjanac.weatherforecastapp.networking.api.MockViewApi
import com.satornjanac.weatherforecastapp.networking.core.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.util.concurrent.Flow
import javax.inject.Inject

/**
 * Sections and forecast repository. First we will get sections part - what widgets we will show on home screen
 * and then we will retrieve weather data from openmeteo api
 */
open class SectionsWithForecastRepository @Inject constructor(
    private val mock: MockViewApi,
    private val forecastApi: ForecastApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    private val mockApis = arrayListOf<String>().apply {
        add("https://run.mocky.io/v3/1f2835b0-9f6b-4a7d-aaac-d270571799e6") // show 1. hourly 2. current 3.daily
        add("https://run.mocky.io/v3/988680e9-3580-4d12-8c4b-a1567dd86768") // show only daily
        add("https://run.mocky.io/v3/c6d894eb-5f90-416a-8bda-b64315c87701") // show 1. daily 2. hourly 3. current 4.daily
    }

    suspend fun getSectionsWithForecast(
        longitude: Double,
        latitude: Double,
        timeZone: String
    ): ApiResult<List<DisplayItems>> {
        return withContext(dispatcher) {
            val number = (0..<mockApis.size).random()
            val sections = mock.getMockViewApi(mockApis[number])
            val responseBody = sections.body()
            if (sections.isSuccessful && responseBody != null) {
                val forecast = forecastApi.getForecast(longitude, latitude, timeZone)
                val forecastBody = forecast.body()
                if (forecast.isSuccessful && forecastBody != null) {

                    val displayItems = buildDisplayItems(responseBody, forecastBody)

                    Success(displayItems)
                } else {
                    Error(forecast.code(), forecast.errorBody()?.string())
                }
            } else {
                Error(sections.code(), sections.errorBody()?.string())
            }
        }
    }

    private fun buildDisplayItems(sections: Sections,
                                  forecast: Forecast
    ): List<DisplayItems> {
        val displayItems = ArrayList<DisplayItems>()

        sections.sections.forEach {section ->

            val item = when (Section.getTypeOrdinal(section.type)) {
                SectionTypes.DAILY_FORECAST.ordinal -> {
                    DisplayItems(section, dailyWeather = forecast.daily, unit = forecast.hourlyUnits.unit)
                }
                SectionTypes.HOURLY_FORECAST.ordinal -> {
                    DisplayItems(section, hourlyWeather = forecast.hourly, unit = forecast.hourlyUnits.unit)
                } else -> {
                    DisplayItems(section, currentWeather = forecast.currentWeather, dailyWeather = forecast.daily, unit = forecast.hourlyUnits.unit)
                }
            }

            displayItems.add(item)

        }

        return displayItems

    }

    fun getForecastAsFlow(longitude: Double,
                          latitude: Double,
                          timeZone: String) = flow {
        val number = (0..<mockApis.size).random()
        val sections = mock.getMockViewApi(mockApis[number])
        val responseBody = sections.body()
        if (sections.isSuccessful && responseBody != null) {
            val forecast = forecastApi.getForecast(longitude, latitude, timeZone)
            val forecastBody = forecast.body()
            if (forecast.isSuccessful && forecastBody != null) {

                val displayItems = buildDisplayItems(responseBody, forecastBody)

                emit(Success(displayItems))
            } else {
                emit(Error(forecast.code(), forecast.errorBody()?.string()))
            }
        } else {
            emit(Error(sections.code(), sections.errorBody()?.string()))
        }
    }.flowOn(dispatcher)

}