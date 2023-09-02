package com.satornjanac.weatherforecastapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.satornjanac.weatherforcastapp.R
import com.satornjanac.weatherforcastapp.databinding.DailyItemBinding
import com.satornjanac.weatherforcastapp.databinding.HourlyItemBinding
import com.satornjanac.weatherforecastapp.extensions.gone
import com.satornjanac.weatherforecastapp.extensions.toDay
import com.satornjanac.weatherforecastapp.extensions.toDayShorterFormat
import com.satornjanac.weatherforecastapp.extensions.toHour
import com.satornjanac.weatherforecastapp.extensions.visible
import com.satornjanac.weatherforecastapp.extensions.weatherCodeIcon
import com.satornjanac.weatherforecastapp.extensions.weatherCodeToString
import com.satornjanac.weatherforecastapp.model.Daily
import com.satornjanac.weatherforecastapp.model.Hourly
import com.satornjanac.weatherforecastapp.model.Section
import com.satornjanac.weatherforecastapp.model.ui.DisplayItems
import kotlin.math.roundToInt

/**
 * Adapter for showing daily weather for next seven days
 */
class DailyAdapter(private val context: Context, private val item: DisplayItems) :
    RecyclerView.Adapter<DailyAdapter.DailyItemViewHolder>() {

    inner class DailyItemViewHolder(val binding: DailyItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyItemViewHolder {
        val item = DailyItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyItemViewHolder(item)
    }

    override fun getItemCount(): Int {
        return 7 // we are showing a full week
    }

    override fun onBindViewHolder(holder: DailyItemViewHolder, position: Int) {

        val section = item.section
        val daily = item.dailyWeather
        daily?.let {
            val dayTempBuffer = StringBuffer()
            if (section.showMinTemp) dayTempBuffer.append(it.temperaturesMin[position].toInt()).append(item.unit)
            if (section.showMinTemp && section.showMaxTemp) dayTempBuffer.append(" / ")
            if (section.showMaxTemp) dayTempBuffer.append(it.temperaturesMax[position].toInt()).append(item.unit)

            if (dayTempBuffer.isNotEmpty()) {
                holder.binding.dayTemperature.text = dayTempBuffer.toString()
                holder.binding.dayTemperature.visible()
            } else {
                holder.binding.dayTemperature.gone()
            }

            if (section.showWeatherCodeIcon) {
                holder.binding.weatherCodeImage.setImageResource(
                    it.weatherCodes[position].weatherCodeIcon(true)
                )
                holder.binding.weatherCodeImage.visible()
            } else {
                holder.binding.weatherCodeImage.gone()
            }

            if (section.showWeatherCodeLabel) {
                holder.binding.dayWeatherCode.text =
                    it.weatherCodes[position].weatherCodeToString(context.resources)
                holder.binding.dayWeatherCode.visible()
            } else {
                holder.binding.dayWeatherCode.gone()
            }

            if (section.showTime) {
                holder.binding.dayLabel.text = if (position != 0) {
                    it.times[position].toDayShorterFormat()
                } else {
                    context.getText(R.string.today)
                }
                holder.binding.dayLabel.visible()
            } else {
                holder.binding.dayLabel.gone()
            }

        }

    }
}