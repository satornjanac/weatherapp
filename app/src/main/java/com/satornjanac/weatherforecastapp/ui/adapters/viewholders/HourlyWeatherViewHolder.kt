package com.satornjanac.weatherforecastapp.ui.adapters.viewholders

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.satornjanac.weatherforcastapp.databinding.HourlyForecastViewBinding
import com.satornjanac.weatherforecastapp.extensions.gone
import com.satornjanac.weatherforecastapp.extensions.visible
import com.satornjanac.weatherforecastapp.model.ui.DisplayItems
import com.satornjanac.weatherforecastapp.ui.adapters.HourlyAdapter

/**
 * Hourly weather widget. UI representation of one date from [com.satornjanac.weatherforecastapp.model.Hourly]
 */
class HourlyWeatherViewHolder(private val binding: HourlyForecastViewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(context: Context, item: DisplayItems) {
        if (item.section.hasTitle()) {
            binding.title.text = item.section.title
            binding.title.visible()
        } else {
            binding.title.gone()
        }

        binding.hourlyWeather.setBackgroundColor(item.section.backgroundColor(context))

        binding.hourlyForecastList.adapter = HourlyAdapter(context, item)
        binding.hourlyForecastList.setHasFixedSize(true)
        binding.hourlyForecastList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }
}