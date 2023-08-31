package com.satornjanac.weatherforecastapp.repo

import com.satornjanac.weatherforecastapp.di.IoDispatcher
import com.satornjanac.weatherforecastapp.model.Sections
import com.satornjanac.weatherforecastapp.networking.core.ApiResult
import com.satornjanac.weatherforecastapp.networking.core.Error
import com.satornjanac.weatherforecastapp.networking.api.MockViewApi
import com.satornjanac.weatherforecastapp.networking.core.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SectionsRepository @Inject constructor(
    private val mock: MockViewApi,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {

    private val mockApis = arrayListOf<String>().apply {
        add("https://run.mocky.io/v3/4a75fdcb-31e8-4ee6-83ae-892b4c1e36bd") // show 1. hourly 2. current 3.daily
        add("https://run.mocky.io/v3/62ed9f37-782a-4e15-9859-f1055a3e9dea") // show only daily
        add("https://run.mocky.io/v3/7d80ac4e-5b2e-4bf4-aeae-9c9ff585ecc4") // show 1. daily 2. hourly 3. current 4.daily
    }

    suspend fun getSections(): ApiResult<Sections> {
        return withContext(dispatcher) {
            val number = (0..<mockApis.size).random()
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