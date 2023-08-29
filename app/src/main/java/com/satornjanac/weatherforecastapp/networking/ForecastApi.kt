package com.satornjanac.weatherforecastapp.networking

import com.satornjanac.weatherforecastapp.model.Forecast
import retrofit2.Response
import retrofit2.http.*

interface ForecastApi {

    companion object {
        const val BASE_URL = "https://api.open-meteo.com/v1/"
    }

    @GET("forecast")
    suspend fun getForecast(
        @Query("longitude") longitude: Double,
        @Query("latitude") latitude: Double,
        @Query("timezone") timezone: String,
        @Query("hourly") hourly: String = "temperature_2m,weathercode,is_day",
        @Query("daily") daily: String = "weathercode,temperature_2m_max,temperature_2m_min",
        @Query("current_weather") currentWeather: Boolean = true): Response<Forecast>

    //https://api.open-meteo.com/v1/forecast?latitude=44.804&longitude=20.4651&hourly=temperature_2m,weathercode,is_day&daily=weathercode,temperature_2m_max,temperature_2m_min&current_weather=true&timezone=Europe%2FBerlin

    //=temperature_2m,weathercode,is_day&current_weather=true&temperature_unit=fahrenheit&windspeed_unit=mph&timezone=Europe%2FBerlin
}