package com.satornjanac.weatherforecastapp.ui.adapters.viewholders

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.satornjanac.weatherforcastapp.databinding.CurrentWeatherViewBinding
import com.satornjanac.weatherforecastapp.extensions.gone
import com.satornjanac.weatherforecastapp.extensions.toDay
import com.satornjanac.weatherforecastapp.extensions.visible
import com.satornjanac.weatherforecastapp.extensions.weatherCodeIcon
import com.satornjanac.weatherforecastapp.extensions.weatherCodeToString
import com.satornjanac.weatherforecastapp.model.IS_DAY
import com.satornjanac.weatherforecastapp.model.ui.DisplayItems
import kotlin.math.roundToInt

class CurrentWeatherViewHolder(private val binding: CurrentWeatherViewBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        context: Context,
        item: DisplayItems
    ) {
        if (item.section.hasTitle()) {
            binding.title.text = item.section.title
            binding.title.visible()
        } else {
            binding.title.gone()
        }

        binding.currentWeather.setBackgroundColor(item.section.backgroundColor(context))

        if (item.section.showTemperature) {
            binding.currentTemperature.text = item.currentWeather?.temperature?.roundToInt().toString()
            binding.currentTemperature.visible()
        } else {
            binding.currentTemperature.gone()
        }

        if (item.section.showWeatherCodeLabel) {
            binding.weatherCode.text =
                item.currentWeather?.weatherCode?.weatherCodeToString(context.resources)
            binding.weatherCode.visible()
        } else {
            binding.weatherCode.gone()
        }


        item.currentWeather?.let {

            if (item.section.showWeatherCodeIcon) {
                binding.currentWeatherCodeIcon.setBackgroundResource(
                    it.weatherCode.weatherCodeIcon(it.isDay == IS_DAY)
                )
                binding.currentWeatherCodeIcon.visible()

            } else {
                binding.currentWeatherCodeIcon.gone()
            }

            if (item.section.showWeatherCodeIcon) {
                binding.currentWeatherCodeIcon.setBackgroundResource(
                    it.weatherCode.weatherCodeIcon(it.isDay == IS_DAY)
                )
                binding.currentWeatherCodeIcon.visible()

            } else {
                binding.currentWeatherCodeIcon.gone()
            }

            val minTemp = (item.dailyWeather?.temperaturesMin?.get(0) ?: 0.0f).roundToInt()
            val maxTemp = (item.dailyWeather?.temperaturesMax?.get(0) ?: 0.0f).roundToInt()

            val dayTempBuffer = StringBuffer()
            if (item.section.showTime) dayTempBuffer.append(it.time.toDay()).append(" ")
            if (item.section.showMinTemp) dayTempBuffer.append(minTemp).append(item.unit)
            if (item.section.showMinTemp && item.section.showMaxTemp) dayTempBuffer.append(" / ")
            if (item.section.showMaxTemp) dayTempBuffer.append(maxTemp).append(item.unit)

            binding.currentDateAndTemp.text = dayTempBuffer.toString()

        }

    }
}