package com.satornjanac.weatherforecastapp.repo

import com.satornjanac.weatherforecastapp.di.IoDispatcher
import com.satornjanac.weatherforecastapp.model.Sections
import com.satornjanac.weatherforecastapp.model.SectionsWithForecast
import com.satornjanac.weatherforecastapp.networking.ApiResult
import com.satornjanac.weatherforecastapp.networking.Error
import com.satornjanac.weatherforecastapp.networking.ForecastApi
import com.satornjanac.weatherforecastapp.networking.MockViewApi
import com.satornjanac.weatherforecastapp.networking.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SectionsWithForecastRepository @Inject constructor(
    private val mock: MockViewApi,
    private val forecastApi: ForecastApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    private val mockApis = arrayListOf<String>().apply {
        add("https://run.mocky.io/v3/4a75fdcb-31e8-4ee6-83ae-892b4c1e36bd") // show 1. hourly 2. current 3.daily
        add("https://run.mocky.io/v3/62ed9f37-782a-4e15-9859-f1055a3e9dea") // show only daily
        add("https://run.mocky.io/v3/c6d894eb-5f90-416a-8bda-b64315c87701") // show 1. daily 2. hourly 3. current 4.daily
    }

    suspend fun getSectionsWithForecast(
        longitude: Double,
        latitude: Double,
        timeZone: String
    ): ApiResult<SectionsWithForecast> {
        return withContext(dispatcher) {
            val number = (0..<mockApis.size).random()
            val sections = mock.getMockViewApi(mockApis[2])
            val responseBody = sections.body()
            if (sections.isSuccessful && responseBody != null) {
                val forecast = forecastApi.getForecast(longitude, latitude, timeZone)
                val forecastBody = forecast.body()
                if (forecast.isSuccessful && forecastBody != null) {
                    Success(SectionsWithForecast(responseBody, forecastBody))
                } else {
                    Error(forecast.code(), forecast.errorBody()?.string())
                }
            } else {
                Error(sections.code(), sections.errorBody()?.string())
            }
        }
    }

}