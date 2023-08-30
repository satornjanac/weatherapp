package com.satornjanac.weatherforecastapp.di

import com.satornjanac.weatherforecastapp.networking.ForecastApi
import com.satornjanac.weatherforecastapp.networking.HeaderAccept
import com.satornjanac.weatherforecastapp.networking.HeaderContentType
import com.satornjanac.weatherforecastapp.networking.MockViewApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.open-meteo.com/v1/"

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addNetworkInterceptor(HeaderContentType())
            .addNetworkInterceptor(HeaderAccept())
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideForecast(retrofit: Retrofit): ForecastApi =
        retrofit.create(ForecastApi::class.java)

    @Singleton
    @Provides
    fun provideMock(retrofit: Retrofit): MockViewApi =
        retrofit.create(MockViewApi::class.java)
}