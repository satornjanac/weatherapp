package com.satornjanac.weatherforecastapp.model

import android.graphics.Color
import com.google.gson.annotations.SerializedName

data class Section(
    @SerializedName("id") val id: Int,
    @SerializedName("type") val type: String,
    @SerializedName("background") val background: String? = null,
    @SerializedName("title") val title : String? = null,
) {
    fun showTitle() = title != null && title != ""

    fun hasColor() = background != null && background != ""

    fun backgroundColor() = Color.parseColor(background)
}

data class Sections(
    @SerializedName("sections") val sections: List<Section>,
)

enum class SectionTypes {
    CURRENT_WEATHER,
    HOURLY_FORECAST,
    DAILY_FORECAST,
}

const val CURRENT_WEATHER = "current_weather"
const val HOURLY_FORECAST = "hourly_forecast"
const val DAILY_FORECAST = "daily_forecast"