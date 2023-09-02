package com.satornjanac.weatherforecastapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.satornjanac.weatherforcastapp.databinding.CurrentWeatherViewBinding
import com.satornjanac.weatherforcastapp.databinding.DailyForecastViewBinding
import com.satornjanac.weatherforcastapp.databinding.HourlyForecastViewBinding
import com.satornjanac.weatherforecastapp.model.Section
import com.satornjanac.weatherforecastapp.model.SectionTypes
import com.satornjanac.weatherforecastapp.model.ui.DisplayItems
import com.satornjanac.weatherforecastapp.ui.adapters.viewholders.CurrentWeatherViewHolder
import com.satornjanac.weatherforecastapp.ui.adapters.viewholders.DailyWeatherViewHolder
import com.satornjanac.weatherforecastapp.ui.adapters.viewholders.HourlyWeatherViewHolder

/**
 * Adapter for holding and showing all weather data: current, weakly and daily
 */
class WeatherAdapter(
    private val context: Context,
    private val displayItems: List<DisplayItems>
) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return when(viewType) {
           SectionTypes.DAILY_FORECAST.ordinal -> {
               val item = DailyForecastViewBinding
                   .inflate(LayoutInflater.from(parent.context), parent, false)
               DailyWeatherViewHolder(item)
           }
           SectionTypes.HOURLY_FORECAST.ordinal -> {
               val item = HourlyForecastViewBinding
                   .inflate(LayoutInflater.from(parent.context), parent, false)
               HourlyWeatherViewHolder(item)
           }
           else -> {
               val item = CurrentWeatherViewBinding
                   .inflate(LayoutInflater.from(parent.context), parent, false)
               CurrentWeatherViewHolder(item)
           }
        }
    }

    override fun getItemCount(): Int {
        return displayItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = displayItems[position]
        when (holder) {
            is CurrentWeatherViewHolder -> {
                holder.bind(context, item)
            }
            is DailyWeatherViewHolder -> {
                holder.bind(context, item)
            }
            is HourlyWeatherViewHolder -> {
                holder.bind(context, item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val type = displayItems[position].section.type
        return Section.getTypeOrdinal(type)
    }

}
