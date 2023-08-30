package com.satornjanac.weatherforecastapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.satornjanac.weatherforcastapp.R
import com.satornjanac.weatherforcastapp.databinding.HourlyItemBinding
import com.satornjanac.weatherforecastapp.extensions.gone
import com.satornjanac.weatherforecastapp.extensions.toHour
import com.satornjanac.weatherforecastapp.extensions.visible
import com.satornjanac.weatherforecastapp.extensions.weatherCodeIcon
import com.satornjanac.weatherforecastapp.extensions.weatherCodeToString
import com.satornjanac.weatherforecastapp.model.Hourly
import com.satornjanac.weatherforecastapp.model.Section

class HourlyAdapter(private val context: Context, private val hourly: Hourly, private val section: Section, private val unit: String) :
    RecyclerView.Adapter<HourlyAdapter.HourlyItemViewHolder>() {

    inner class HourlyItemViewHolder(val binding: HourlyItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyItemViewHolder {
        val item = HourlyItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return HourlyItemViewHolder(item)
    }

    override fun getItemCount(): Int {
        return 24 // we are showing a full day
    }

    override fun onBindViewHolder(holder: HourlyItemViewHolder, position: Int) {
        if (section.showTime) {
            holder.binding.hourLabel.text = if (position != 0) {
                hourly.times[position].toHour()
            } else {
                context.getText(R.string.now)
            }
            holder.binding.hourLabel.visible()
        } else {
            holder.binding.hourLabel.gone()
        }

        if (section.showWeatherCodeIcon) {
            holder.binding.weatherCodeImage.setImageResource(
                hourly.weatherCodes[position].weatherCodeIcon(
                    hourly.isDay[position] == 1
                )
            )
            holder.binding.weatherCodeImage.visible()
        } else {
            holder.binding.weatherCodeImage.gone()
        }

        if (section.showWeatherCodeLabel) {
            holder.binding.weatherCodeLabel.text =
                hourly.weatherCodes[position].weatherCodeToString(context.resources)
            holder.binding.weatherCodeLabel.visible()
        } else {
            holder.binding.weatherCodeLabel.gone()
        }

        if (section.showTemperature) {
            holder.binding.currentTemperature.text =
                context.getString(R.string.temperature, hourly.temperatures[position].toInt(), unit)
        }

    }
}