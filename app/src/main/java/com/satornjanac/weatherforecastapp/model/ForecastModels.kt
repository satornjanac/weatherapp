package com.satornjanac.weatherforecastapp.model

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName("time") val time: String,
    @SerializedName("temperature") val temperature: Float,
    @SerializedName("weathercode") val weatherCode: Int,
    @SerializedName("windspeed") val windSpeed: Float,
    @SerializedName("is_day") val isDay: Int
)

data class Hourly(
    @SerializedName("time") val times: List<String>,
    @SerializedName( "temperature_2m") val temperatures: List<Float>,
    @SerializedName("weathercode") val weatherCodes: List<Int>,
    @SerializedName("is_day") val isDay: List<Int>,
)

data class HourlyUnits(
    @SerializedName("temperature_2m") val unit: String
)

data class DailyUnits(
    @SerializedName("temperature_2m_max") val maxUnit: String,
    @SerializedName("temperature_2m_min") val minUnit: String
)

data class Daily(
    @SerializedName("time") val times: List<String>,
    @SerializedName( "temperature_2m_max") val temperaturesMax: List<Float>,
    @SerializedName( "temperature_2m_min") val temperaturesMin: List<Float>,
    @SerializedName("weathercode") val weatherCodes: List<Int>,
)

data class Forecast(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("timezone_abbreviation") val timezoneAbbreviation: String,
    @SerializedName("hourly_units") val hourlyUnits: HourlyUnits,
    @SerializedName("hourly") val hourly: Hourly,
    @SerializedName("current_weather") val currentWeather: CurrentWeather,
    @SerializedName("daily_units") val dailyUnits: DailyUnits,
    @SerializedName("daily") val daily: Daily,
)
