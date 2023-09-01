package com.satornjanac.weatherforecastapp.fake

import com.satornjanac.weatherforecastapp.model.CURRENT_WEATHER
import com.satornjanac.weatherforecastapp.model.HOURLY_FORECAST
import com.satornjanac.weatherforecastapp.model.Section
import com.satornjanac.weatherforecastapp.model.Sections
import com.satornjanac.weatherforecastapp.networking.api.MockViewApi
import retrofit2.Response
import javax.inject.Inject

class FakeMockViewApi @Inject constructor(): MockViewApi {

    override suspend fun getMockViewApi(url: String): Response<Sections> {
        val dailySection = Section(
            1,
            CURRENT_WEATHER,
            "#f0f0f0",
            "TestCurrentSection",
            showTemperature = true,
            showWeatherCodeIcon = false,
            showWeatherCodeLabel = true,
            showMinTemp = false,
            showMaxTemp = true,
            showTime = true
        )

        val hourlySection = Section(
            2,
            HOURLY_FORECAST,
            "#f1f1f1",
            "TestHourlySection",
            showTemperature = true,
            showWeatherCodeIcon = false,
            showWeatherCodeLabel = true,
            showMinTemp = false,
            showMaxTemp = true,
            showTime = true
        )
        val arrayOfSection = arrayListOf<Section>()
        arrayOfSection.add(dailySection)
        arrayOfSection.add(hourlySection)
        val sections = Sections(arrayOfSection)
        return Response.success(sections)
    }
}