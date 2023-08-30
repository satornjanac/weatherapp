package com.satornjanac.weatherforecastapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.satornjanac.weatherforcastapp.R
import com.satornjanac.weatherforcastapp.databinding.CurrentWeatherViewBinding
import com.satornjanac.weatherforcastapp.databinding.DailyForecastViewBinding
import com.satornjanac.weatherforcastapp.databinding.HourlyForecastViewBinding
import com.satornjanac.weatherforecastapp.extensions.gone
import com.satornjanac.weatherforecastapp.extensions.toDay
import com.satornjanac.weatherforecastapp.extensions.visible
import com.satornjanac.weatherforecastapp.extensions.weatherCodeIcon
import com.satornjanac.weatherforecastapp.extensions.weatherCodeToString
import com.satornjanac.weatherforecastapp.model.CurrentWeather
import com.satornjanac.weatherforecastapp.model.Daily
import com.satornjanac.weatherforecastapp.model.Hourly
import com.satornjanac.weatherforecastapp.model.Section
import com.satornjanac.weatherforecastapp.model.SectionTypes
import com.satornjanac.weatherforecastapp.model.SectionsWithForecast
import kotlin.math.roundToInt

class WeatherAdapter(
    val context: Context,
    private val sectionsAndForecast: SectionsWithForecast
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
        return sectionsAndForecast.sections.sections.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val section = sectionsAndForecast.sections.sections[position]
        when (holder) {
            is CurrentWeatherViewHolder -> {
                val currentWeather = sectionsAndForecast.forecast.currentWeather
                val daily = sectionsAndForecast.forecast.daily
                holder.bind(currentWeather, section, daily.temperaturesMin[0], daily.temperaturesMax[0], sectionsAndForecast.forecast.dailyUnits.minUnit)
            }
            is DailyWeatherViewHolder -> {
                val daily = sectionsAndForecast.forecast.daily
                val unit = sectionsAndForecast.forecast.dailyUnits.minUnit
                holder.bind(daily, section, unit)
            }
            is HourlyWeatherViewHolder -> {
                val hourly = sectionsAndForecast.forecast.hourly
                val unit = sectionsAndForecast.forecast.hourlyUnits.unit
                holder.bind(hourly, section, unit)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val section = sectionsAndForecast.sections.sections[position]
        return Section.getTypeOrdinal(section.type)
    }

    inner class DailyWeatherViewHolder(val binding: DailyForecastViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(daily: Daily, section: Section, unit: String) {
            if (section.hasTitle()) {
                binding.title.text = section.title
                binding.title.visible()
            } else {
                binding.title.gone()
            }

            binding.dailyWeather.setBackgroundColor(section.backgroundColor(context))

            binding.dailyForecastList.adapter = DailyAdapter(context, daily, section, unit)
            binding.dailyForecastList.setHasFixedSize(true)
            binding.dailyForecastList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    inner class CurrentWeatherViewHolder(val binding: CurrentWeatherViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            currentWeather: CurrentWeather,
            section: Section,
            min: Float,
            max: Float,
            unit: String
        ) {
            if (section.hasTitle()) {
                binding.title.text = section.title
                binding.title.visible()
            } else {
                binding.title.gone()
            }

            binding.currentWeather.setBackgroundColor(section.backgroundColor(context))

            if (section.showTemperature) {
                binding.currentTemperature.text = currentWeather.temperature.roundToInt().toString()
                binding.currentTemperature.visible()
            } else {
                binding.currentTemperature.gone()
            }

            if (section.showWeatherCodeLabel) {
                binding.weatherCode.text =
                    currentWeather.weatherCode.weatherCodeToString(context.resources)
                binding.weatherCode.visible()
            } else {
                binding.weatherCode.gone()
            }

            if (section.showWeatherCodeIcon) {
                binding.currentWeatherCodeIcon.setBackgroundResource(
                    currentWeather.weatherCode.weatherCodeIcon(
                        currentWeather.isDay == 1
                    )
                )
                binding.currentWeatherCodeIcon.visible()
            } else {
                binding.currentWeatherCodeIcon.gone()
            }

            val dayTempBuffer = StringBuffer()
            if (section.showTime) dayTempBuffer.append(currentWeather.time.toDay()).append(" ")
            if (section.showMinTemp) dayTempBuffer.append(min.roundToInt()).append(unit)
            if (section.showMinTemp && section.showMaxTemp) dayTempBuffer.append(" / ")
            if (section.showMaxTemp) dayTempBuffer.append(max.roundToInt()).append(unit)

            binding.currentDateAndTemp.text = dayTempBuffer.toString()
        }
    }

    inner class HourlyWeatherViewHolder(val binding: HourlyForecastViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hourly: Hourly, section: Section, unit: String) {
            if (section.hasTitle()) {
                binding.title.text = section.title
                binding.title.visible()
            } else {
                binding.title.gone()
            }

            binding.hourlyWeather.setBackgroundColor(section.backgroundColor(context))

            binding.hourlyForecastList.adapter = HourlyAdapter(context, hourly, section, unit)
            binding.hourlyForecastList.setHasFixedSize(true)
            binding.hourlyForecastList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

}
