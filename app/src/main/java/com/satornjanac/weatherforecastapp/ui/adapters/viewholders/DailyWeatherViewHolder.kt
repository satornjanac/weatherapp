package com.satornjanac.weatherforecastapp.ui.adapters.viewholders

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.satornjanac.weatherforcastapp.databinding.DailyForecastViewBinding
import com.satornjanac.weatherforecastapp.extensions.gone
import com.satornjanac.weatherforecastapp.extensions.visible
import com.satornjanac.weatherforecastapp.model.ui.DisplayItems
import com.satornjanac.weatherforecastapp.ui.adapters.DailyAdapter

class DailyWeatherViewHolder(private val binding: DailyForecastViewBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(context: Context, item: DisplayItems) {
        if (item.section.hasTitle()) {
            binding.title.text = item.section.title
            binding.title.visible()
        } else {
            binding.title.gone()
        }

        binding.dailyWeather.setBackgroundColor(item.section.backgroundColor(context))

        binding.dailyForecastList.adapter = DailyAdapter(context, item)
        binding.dailyForecastList.setHasFixedSize(true)
        binding.dailyForecastList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}
