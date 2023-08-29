package com.satornjanac.weatherforecastapp.networking

import com.satornjanac.weatherforecastapp.model.Sections
import retrofit2.Response
import retrofit2.http.*

interface MockViewApi {

    @GET
    suspend fun getMockViewApi(@Url url:String): Response<Sections>
}