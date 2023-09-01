package com.satornjanac.weatherforecastapp.fake
import com.satornjanac.weatherforecastapp.di.AppModule
import com.satornjanac.weatherforecastapp.networking.api.ForecastApi
import com.satornjanac.weatherforecastapp.networking.api.MockViewApi
import com.satornjanac.weatherforecastapp.repo.SectionsWithForecastRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
interface TestAppModule {

    @Binds
    fun bindsFakeRepository(fakeSectionsWithForecastRepository: FakeSectionsWithForecastRepository): SectionsWithForecastRepository

    @Binds
    fun bindsFakeForecastApi(fakeForecastApi: FakeForecastApi): ForecastApi

    @Binds
    fun bindsFakeMockViewApi(fakeForecastApi: FakeMockViewApi): MockViewApi


}