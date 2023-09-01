package com.satornjanac.weatherforecastapp.fake

import com.google.gson.Gson
import com.satornjanac.weatherforecastapp.model.Forecast
import com.satornjanac.weatherforecastapp.networking.api.ForecastApi
import retrofit2.Response
import javax.inject.Inject

class FakeForecastApi  @Inject constructor() : ForecastApi {

    // this is copy from one of the response from open meteo
    private val fakeResponseString =
        "{\"latitude\":44.8125,\"longitude\":20.4375,\"generationtime_ms\":0.7940530776977539,\"utc_offset_seconds\":7200,\"timezone\":\"Europe/Berlin\",\"timezone_abbreviation\":\"CEST\",\"elevation\":120.0,\"current_weather\":{\"temperature\":26.9,\"windspeed\":8.6,\"winddirection\":112,\"weathercode\":3,\"is_day\":1,\"time\":\"2023-08-29T14:00\"},\"hourly_units\":{\"time\":\"iso8601\",\"temperature_2m\":\"°C\",\"weathercode\":\"wmo code\",\"is_day\":\"\"},\"hourly\":{\"time\":[\"2023-08-29T00:00\",\"2023-08-29T01:00\",\"2023-08-29T02:00\",\"2023-08-29T03:00\",\"2023-08-29T04:00\",\"2023-08-29T05:00\",\"2023-08-29T06:00\",\"2023-08-29T07:00\",\"2023-08-29T08:00\",\"2023-08-29T09:00\",\"2023-08-29T10:00\",\"2023-08-29T11:00\",\"2023-08-29T12:00\",\"2023-08-29T13:00\",\"2023-08-29T14:00\",\"2023-08-29T15:00\",\"2023-08-29T16:00\",\"2023-08-29T17:00\",\"2023-08-29T18:00\",\"2023-08-29T19:00\",\"2023-08-29T20:00\",\"2023-08-29T21:00\",\"2023-08-29T22:00\",\"2023-08-29T23:00\",\"2023-08-30T00:00\",\"2023-08-30T01:00\",\"2023-08-30T02:00\",\"2023-08-30T03:00\",\"2023-08-30T04:00\",\"2023-08-30T05:00\",\"2023-08-30T06:00\",\"2023-08-30T07:00\",\"2023-08-30T08:00\",\"2023-08-30T09:00\",\"2023-08-30T10:00\",\"2023-08-30T11:00\",\"2023-08-30T12:00\",\"2023-08-30T13:00\",\"2023-08-30T14:00\",\"2023-08-30T15:00\",\"2023-08-30T16:00\",\"2023-08-30T17:00\",\"2023-08-30T18:00\",\"2023-08-30T19:00\",\"2023-08-30T20:00\",\"2023-08-30T21:00\",\"2023-08-30T22:00\",\"2023-08-30T23:00\",\"2023-08-31T00:00\",\"2023-08-31T01:00\",\"2023-08-31T02:00\",\"2023-08-31T03:00\",\"2023-08-31T04:00\",\"2023-08-31T05:00\",\"2023-08-31T06:00\",\"2023-08-31T07:00\",\"2023-08-31T08:00\",\"2023-08-31T09:00\",\"2023-08-31T10:00\",\"2023-08-31T11:00\",\"2023-08-31T12:00\",\"2023-08-31T13:00\",\"2023-08-31T14:00\",\"2023-08-31T15:00\",\"2023-08-31T16:00\",\"2023-08-31T17:00\",\"2023-08-31T18:00\",\"2023-08-31T19:00\",\"2023-08-31T20:00\",\"2023-08-31T21:00\",\"2023-08-31T22:00\",\"2023-08-31T23:00\",\"2023-09-01T00:00\",\"2023-09-01T01:00\",\"2023-09-01T02:00\",\"2023-09-01T03:00\",\"2023-09-01T04:00\",\"2023-09-01T05:00\",\"2023-09-01T06:00\",\"2023-09-01T07:00\",\"2023-09-01T08:00\",\"2023-09-01T09:00\",\"2023-09-01T10:00\",\"2023-09-01T11:00\",\"2023-09-01T12:00\",\"2023-09-01T13:00\",\"2023-09-01T14:00\",\"2023-09-01T15:00\",\"2023-09-01T16:00\",\"2023-09-01T17:00\",\"2023-09-01T18:00\",\"2023-09-01T19:00\",\"2023-09-01T20:00\",\"2023-09-01T21:00\",\"2023-09-01T22:00\",\"2023-09-01T23:00\",\"2023-09-02T00:00\",\"2023-09-02T01:00\",\"2023-09-02T02:00\",\"2023-09-02T03:00\",\"2023-09-02T04:00\",\"2023-09-02T05:00\",\"2023-09-02T06:00\",\"2023-09-02T07:00\",\"2023-09-02T08:00\",\"2023-09-02T09:00\",\"2023-09-02T10:00\",\"2023-09-02T11:00\",\"2023-09-02T12:00\",\"2023-09-02T13:00\",\"2023-09-02T14:00\",\"2023-09-02T15:00\",\"2023-09-02T16:00\",\"2023-09-02T17:00\",\"2023-09-02T18:00\",\"2023-09-02T19:00\",\"2023-09-02T20:00\",\"2023-09-02T21:00\",\"2023-09-02T22:00\",\"2023-09-02T23:00\",\"2023-09-03T00:00\",\"2023-09-03T01:00\",\"2023-09-03T02:00\",\"2023-09-03T03:00\",\"2023-09-03T04:00\",\"2023-09-03T05:00\",\"2023-09-03T06:00\",\"2023-09-03T07:00\",\"2023-09-03T08:00\",\"2023-09-03T09:00\",\"2023-09-03T10:00\",\"2023-09-03T11:00\",\"2023-09-03T12:00\",\"2023-09-03T13:00\",\"2023-09-03T14:00\",\"2023-09-03T15:00\",\"2023-09-03T16:00\",\"2023-09-03T17:00\",\"2023-09-03T18:00\",\"2023-09-03T19:00\",\"2023-09-03T20:00\",\"2023-09-03T21:00\",\"2023-09-03T22:00\",\"2023-09-03T23:00\",\"2023-09-04T00:00\",\"2023-09-04T01:00\",\"2023-09-04T02:00\",\"2023-09-04T03:00\",\"2023-09-04T04:00\",\"2023-09-04T05:00\",\"2023-09-04T06:00\",\"2023-09-04T07:00\",\"2023-09-04T08:00\",\"2023-09-04T09:00\",\"2023-09-04T10:00\",\"2023-09-04T11:00\",\"2023-09-04T12:00\",\"2023-09-04T13:00\",\"2023-09-04T14:00\",\"2023-09-04T15:00\",\"2023-09-04T16:00\",\"2023-09-04T17:00\",\"2023-09-04T18:00\",\"2023-09-04T19:00\",\"2023-09-04T20:00\",\"2023-09-04T21:00\",\"2023-09-04T22:00\",\"2023-09-04T23:00\"],\"temperature_2m\":[29.5,28.8,27.0,25.0,24.3,23.6,22.8,22.6,23.0,23.4,23.3,24.3,24.8,26.3,26.9,27.8,27.8,26.5,26.1,24.8,23.9,23.5,23.0,22.4,21.7,20.8,20.0,19.5,19.0,18.5,18.3,19.4,21.5,23.3,24.8,26.3,27.0,27.7,28.0,28.1,28.1,26.1,23.8,23.6,22.7,22.1,21.1,19.8,19.3,19.0,18.5,18.4,18.3,18.3,18.2,18.2,18.5,19.2,20.3,21.4,22.1,22.8,23.8,24.4,24.7,24.1,23.2,22.1,20.7,19.6,18.8,18.3,17.7,17.2,16.7,16.5,16.4,16.1,15.6,15.5,17.2,19.7,21.7,23.5,24.8,25.8,26.5,27.0,27.1,26.8,25.9,24.5,23.1,21.8,20.6,19.5,18.6,17.8,17.2,16.6,16.1,16.0,16.3,17.0,18.2,20.5,23.4,25.8,27.4,28.5,29.2,29.7,29.8,29.4,28.3,26.8,25.3,24.0,22.9,21.8,20.9,20.2,19.5,18.8,18.1,17.9,18.5,19.6,20.4,22.8,25.4,27.6,29.1,30.2,30.8,31.1,31.0,30.4,29.1,27.3,25.6,24.3,23.1,22.2,21.7,21.4,21.1,20.5,19.9,19.8,20.6,22.0,23.6,25.5,27.6,29.4,30.7,31.6,32.2,32.5,32.4,31.7,30.1,27.8,25.8,24.4,23.2,22.3],\"weathercode\":[2,1,1,2,3,3,3,3,3,3,61,3,3,3,3,3,3,80,3,3,3,3,2,2,0,0,0,0,0,1,3,2,2,3,3,3,2,2,2,2,2,3,80,2,2,1,2,80,80,3,3,3,3,3,3,45,80,80,80,80,3,2,2,2,2,2,2,1,0,0,0,1,2,2,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,1,1,1,2,2,2,2,2,2,1,1,1,1,1,1,1,1,1,2,2,2,3,3,3,2,2,2,0,0,0,1,1,1,1,1,1,1,1,1,2,2,2,0,0,0],\"is_day\":[0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0]},\"daily_units\":{\"time\":\"iso8601\",\"weathercode\":\"wmo code\",\"temperature_2m_max\":\"°C\",\"temperature_2m_min\":\"°C\"},\"daily\":{\"time\":[\"2023-08-29\",\"2023-08-30\",\"2023-08-31\",\"2023-09-01\",\"2023-09-02\",\"2023-09-03\",\"2023-09-04\"],\"weathercode\":[80,80,80,2,1,2,3],\"temperature_2m_max\":[29.5,28.1,24.7,27.1,29.8,31.1,32.5],\"temperature_2m_min\":[22.4,18.3,18.2,15.5,16.0,17.9,19.8]}}"

    override suspend fun getForecast(
        longitude: Double,
        latitude: Double,
        timezone: String,
        hourly: String,
        daily: String,
        currentWeather: Boolean
    ): Response<Forecast> {
        val gson = Gson()
        val forecast = gson.fromJson(fakeResponseString, Forecast::class.java)
        return Response.success(forecast)
    }
}