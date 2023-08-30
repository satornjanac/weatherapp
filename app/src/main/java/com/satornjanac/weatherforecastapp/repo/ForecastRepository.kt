package com.satornjanac.weatherforecastapp.repo

import com.satornjanac.weatherforecastapp.di.IoDispatcher
import com.satornjanac.weatherforecastapp.model.Forecast
import com.satornjanac.weatherforecastapp.networking.ApiResult
import com.satornjanac.weatherforecastapp.networking.Error
import com.satornjanac.weatherforecastapp.networking.ForecastApi
import com.satornjanac.weatherforecastapp.networking.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val forecastApi: ForecastApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    suspend fun getForecast(
        longitude: Double,
        latitude: Double,
        timeZone: String
    ): ApiResult<Forecast> {
        return withContext(dispatcher) {
            val forecast = forecastApi.getForecast(longitude, latitude, timeZone)
            val responseBody = forecast.body()
            if (forecast.isSuccessful && responseBody != null) {
                Success(responseBody)
            } else {
                Error(forecast.code(), "Something somewhere went terrible wrong")
            }
        }
    }

}