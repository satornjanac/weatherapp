package com.satornjanac.weatherforecastapp

import com.satornjanac.weatherforecastapp.fake.FakeForecastApi
import com.satornjanac.weatherforecastapp.fake.FakeMockViewApi
import com.satornjanac.weatherforecastapp.fake.FakeSectionsWithForecastRepository
import com.satornjanac.weatherforecastapp.model.CURRENT_WEATHER
import com.satornjanac.weatherforecastapp.model.HOURLY_FORECAST
import com.satornjanac.weatherforecastapp.networking.core.Success
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
class DisplayItemsTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testScope = TestScope(UnconfinedTestDispatcher())

    @BindValue
    lateinit var repository: FakeSectionsWithForecastRepository

    @Before
    fun setup() {
        repository = FakeSectionsWithForecastRepository(FakeMockViewApi(), FakeForecastApi(), Dispatchers.IO)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testDisplayItemsCreation() {
        testScope.runTest {
            val response = repository.getSectionsWithForecast(0.0, 0.0, "")
            Assert.assertTrue(response is Success)

            if (response is Success) {
                val data = response.data

                // test section part
                Assert.assertTrue(data.size == 2)
                val dailySection = data[0].section
                Assert.assertTrue(dailySection.type == CURRENT_WEATHER)
                Assert.assertTrue(dailySection.title == "TestCurrentSection")
                Assert.assertTrue(dailySection.background == "#f0f0f0")
                Assert.assertTrue(dailySection.showTemperature)
                Assert.assertFalse(dailySection.showWeatherCodeIcon)
                Assert.assertTrue(dailySection.showWeatherCodeLabel)
                Assert.assertFalse(dailySection.showMinTemp)
                Assert.assertTrue(dailySection.showMaxTemp)
                Assert.assertTrue(dailySection.showTime)

                // test data part
                Assert.assertTrue(data[0].currentWeather != null)
                Assert.assertTrue(data[0].dailyWeather != null) // we need daily weather if there is min / max temp on UI
                Assert.assertTrue(data[0].hourlyWeather == null)

                val currentWeather = data[0].currentWeather
                Assert.assertTrue(currentWeather?.temperature  == 26.9f)
                Assert.assertTrue(currentWeather?.windSpeed  == 8.6f)
                Assert.assertTrue(currentWeather?.time  == "2023-08-29T14:00")
                Assert.assertTrue(currentWeather?.weatherCode  == 3)
                Assert.assertTrue(currentWeather?.isDay == 1)

                Assert.assertTrue(data[0].unit == "°C")

                val daily = data[0].dailyWeather
                Assert.assertTrue(daily?.temperaturesMax?.size == 7)
                Assert.assertTrue(daily?.temperaturesMin?.size == 7)
                Assert.assertTrue(daily?.temperaturesMax?.get(0) == 29.5f)
                Assert.assertTrue(daily?.temperaturesMin?.get(0) == 22.4f)

                val hourlySection = data[1].section
                Assert.assertTrue(hourlySection.type == HOURLY_FORECAST)
                Assert.assertTrue(hourlySection.title == "TestHourlySection")
                Assert.assertTrue(hourlySection.background == "#f1f1f1")
                Assert.assertTrue(hourlySection.showTemperature)
                Assert.assertFalse(hourlySection.showWeatherCodeIcon)
                Assert.assertTrue(hourlySection.showWeatherCodeLabel)
                Assert.assertFalse(hourlySection.showMinTemp)
                Assert.assertTrue(hourlySection.showMaxTemp)
                Assert.assertTrue(hourlySection.showTime)

                // test data part
                Assert.assertTrue(data[1].currentWeather == null)
                Assert.assertTrue(data[1].dailyWeather == null)
                Assert.assertTrue(data[1].hourlyWeather != null)

                val hourly = data[1].hourlyWeather
                val hourlySize = 7 * 24
                Assert.assertTrue(hourly?.temperatures?.size == hourlySize)
                Assert.assertTrue(hourly?.isDay?.size == hourlySize)
                Assert.assertTrue(hourly?.times?.size == hourlySize)
                Assert.assertTrue(hourly?.weatherCodes?.size == hourlySize)

                Assert.assertTrue(hourly?.temperatures?.get(0) == 29.5f)
                Assert.assertTrue(hourly?.isDay?.get(0) == 0)
                Assert.assertTrue(hourly?.times?.get(0) == "2023-08-29T00:00")
                Assert.assertTrue(hourly?.weatherCodes?.get(0) == 2)
            }
        }
    }
}