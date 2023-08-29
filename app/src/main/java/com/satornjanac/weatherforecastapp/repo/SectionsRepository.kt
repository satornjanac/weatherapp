package com.satornjanac.weatherforecastapp.repo

import com.satornjanac.weatherforecastapp.di.IoDispatcher
import com.satornjanac.weatherforecastapp.model.Sections
import com.satornjanac.weatherforecastapp.networking.ApiResult
import com.satornjanac.weatherforecastapp.networking.Error
import com.satornjanac.weatherforecastapp.networking.MockViewApi
import com.satornjanac.weatherforecastapp.networking.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SectionsRepository @Inject constructor(
    private val mock: MockViewApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    private val mockApis = arrayListOf<String>().apply {
        add("https://run.mocky.io/v3/4d95a828-6033-4c15-a59f-ca503904b0a3") // show 1. current weather 2.hourly
        add("https://run.mocky.io/v3/ad8d64e3-0a75-4bdc-89a8-216b804bb665") // show 1. hourly 2. current 3.daily
        add("https://run.mocky.io/v3/6a08be09-1afe-4ce7-94a3-6d2d1473cc07") // show only daily
    }

    suspend fun getSections(): ApiResult<Sections> {
        return withContext(dispatcher) {
            val number = (0..2).random()
            val sections = mock.getMockViewApi(mockApis[number])
            val responseBody = sections.body()
            if (sections.isSuccessful && responseBody != null) {
                Success(responseBody)
            } else {
                Error(sections.code(), sections.errorBody()?.string())
            }
        }
    }

}