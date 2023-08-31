package com.satornjanac.weatherforecastapp.model.ui

import com.satornjanac.weatherforecastapp.model.CurrentWeather
import com.satornjanac.weatherforecastapp.model.Daily
import com.satornjanac.weatherforecastapp.model.Hourly
import com.satornjanac.weatherforecastapp.model.Section

data class DisplayItems(
    val section: Section,
    val currentWeather: CurrentWeather? = null,
    val hourlyWeather: Hourly? = null,
    val dailyWeather: Daily? = null,
    val unit: String= ""
)
