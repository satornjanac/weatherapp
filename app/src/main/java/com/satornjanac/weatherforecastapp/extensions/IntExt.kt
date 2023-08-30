package com.satornjanac.weatherforecastapp.extensions

import android.content.res.Resources
import com.satornjanac.weatherforcastapp.R

fun Int.weatherCodeToString(resources: Resources): String {
    return when (this) {
        0 -> resources.getString(R.string.weather_code_0)
        1 -> resources.getString(R.string.weather_code_1)
        2 -> resources.getString(R.string.weather_code_2)
        3 -> resources.getString(R.string.weather_code_3)
        45 -> resources.getString(R.string.weather_code_45)
        48 -> resources.getString(R.string.weather_code_48)
        51 -> resources.getString(R.string.weather_code_51)
        53 -> resources.getString(R.string.weather_code_53)
        55 -> resources.getString(R.string.weather_code_55)
        56 -> resources.getString(R.string.weather_code_56)
        57 -> resources.getString(R.string.weather_code_57)
        61 -> resources.getString(R.string.weather_code_61)
        63 -> resources.getString(R.string.weather_code_63)
        65 -> resources.getString(R.string.weather_code_65)
        66 -> resources.getString(R.string.weather_code_66)
        67 -> resources.getString(R.string.weather_code_67)
        71 -> resources.getString(R.string.weather_code_71)
        73 -> resources.getString(R.string.weather_code_73)
        75 -> resources.getString(R.string.weather_code_75)
        77 -> resources.getString(R.string.weather_code_77)
        80 -> resources.getString(R.string.weather_code_80)
        81 -> resources.getString(R.string.weather_code_81)
        82 -> resources.getString(R.string.weather_code_82)
        85 -> resources.getString(R.string.weather_code_85)
        86 -> resources.getString(R.string.weather_code_86)
        95 -> resources.getString(R.string.weather_code_95)
        96 -> resources.getString(R.string.weather_code_96)
        else -> resources.getString(R.string.weather_code_99)
    }
}

fun Int.weatherCodeIcon(isDay: Boolean): Int {
    return when (this) {
        0 -> {
            if (isDay) {
                R.drawable.wmo_0_day
            } else {
                R.drawable.wmo_0_night
            }
        }
        1 -> {
            if (isDay) {
                R.drawable.wmo_1_day
            } else {
                R.drawable.wmo_1_night
            }
        }
        2 -> {
            if (isDay) {
                R.drawable.wmo_2_day
            } else {
                R.drawable.wmo_2_night
            }
        }
        3 -> R.drawable.wmo_3
        45 -> R.drawable.wmo_45
        48 -> R.drawable.wmo_48
        51 -> R.drawable.wmo_51
        53 -> R.drawable.wmo_53
        55 -> R.drawable.wmo_55
        56 -> R.drawable.wmo_56
        57 -> R.drawable.wmo_57
        61 -> R.drawable.wmo_61
        63 -> R.drawable.wmo_63
        65 -> R.drawable.wmo_65
        66 -> R.drawable.wmo_66
        67 -> R.drawable.wmo_67
        71 -> R.drawable.wmo_71
        73 -> R.drawable.wmo_73
        75 -> R.drawable.wmo_75
        77 -> R.drawable.wmo_77
        80 -> {
            if (isDay) {
                R.drawable.wmo_80_day
            } else {
                R.drawable.wmo_80_night
            }
        }
        81 -> {
            if (isDay) {
                R.drawable.wmo_81_day
            } else {
                R.drawable.wmo_81_night
            }
        }
        82 -> {
            if (isDay) {
                R.drawable.wmo_82_day
            } else {
                R.drawable.wmo_82_night
            }
        }
        85 -> {
            if (isDay) {
                R.drawable.wmo_85_day
            } else {
                R.drawable.wmo_85_night
            }
        }
        86 -> {
            if (isDay) {
                R.drawable.wmo_86_day
            } else {
                R.drawable.wmo_86_night
            }
        }
        95 -> R.drawable.wmo_95
        96 -> R.drawable.wmo_96
        else -> R.drawable.wmo_99
    }
}

/*
0	Clear sky
1, 2, 3	Mainly clear, partly cloudy, and overcast
45, 48	Fog and depositing rime fog
51, 53, 55	Drizzle: Light, moderate, and dense intensity
56, 57	Freezing Drizzle: Light and dense intensity
61, 63, 65	Rain: Slight, moderate and heavy intensity
66, 67	Freezing Rain: Light and heavy intensity
71, 73, 75	Snow fall: Slight, moderate, and heavy intensity
77	Snow grains
80, 81, 82	Rain showers: Slight, moderate, and violent
85, 86	Snow showers slight and heavy
95 *	Thunderstorm: Slight or moderate
96, 99 *	Thunderstorm with slight and heavy hail
 */