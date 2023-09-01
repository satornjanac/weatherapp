package com.satornjanac.weatherforecastapp.fake

import com.satornjanac.weatherforecastapp.di.IoDispatcher
import com.satornjanac.weatherforecastapp.repo.SectionsWithForecastRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class FakeSectionsWithForecastRepository @Inject constructor(
    private val mock: FakeMockViewApi,
    private val forecastApi: FakeForecastApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : SectionsWithForecastRepository(mock, forecastApi, dispatcher) {


}