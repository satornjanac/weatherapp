package com.satornjanac.weatherforecastapp.model

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import com.google.gson.annotations.SerializedName
import com.satornjanac.weatherforcastapp.R

data class Section(
    @SerializedName("id") val id: Int,
    @SerializedName("type") val type: String,
    @SerializedName("background") val background: String? = null,
    @SerializedName("title") val title : String? = null,
    @SerializedName("show_temperature") val showTemperature : Boolean = true,
    @SerializedName("show_wcode_icon") val showWeatherCodeIcon : Boolean = true,
    @SerializedName("show_wcode_label") val showWeatherCodeLabel : Boolean = true,
    @SerializedName("show_min_temp") val showMinTemp : Boolean = true,
    @SerializedName("show_max_temp") val showMaxTemp : Boolean = true,
    @SerializedName("show_time") val showTime : Boolean = true,
) {
    fun hasTitle() = title != null && title != ""

    fun hasColor() = background != null && background != ""

    fun backgroundColor(context: Context) =
        if (hasColor()) Color.parseColor(background) else ContextCompat.getColor(
            context,
            R.color.cardViewBackground
        )

    companion object {
        fun getTypeOrdinal(type: String) =
            when (type) {
                CURRENT_WEATHER -> SectionTypes.CURRENT_WEATHER.ordinal
                HOURLY_FORECAST -> SectionTypes.HOURLY_FORECAST.ordinal
                DAILY_FORECAST -> SectionTypes.DAILY_FORECAST.ordinal
                else -> SectionTypes.CURRENT_WEATHER.ordinal
            }
    }
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

