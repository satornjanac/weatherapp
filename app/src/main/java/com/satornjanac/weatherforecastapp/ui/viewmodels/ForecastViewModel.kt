package com.satornjanac.weatherforecastapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satornjanac.weatherforecastapp.model.CurrentWeather
import com.satornjanac.weatherforecastapp.networking.Error
import com.satornjanac.weatherforecastapp.networking.Success
import com.satornjanac.weatherforecastapp.repo.ForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(private val forecastRepository: ForecastRepository) :
    ViewModel() {

    // showing all error dialogs regarding mixes
    private val _errors = MutableLiveData<String?>()
    val errors: LiveData<String?> = _errors
    private val _currentWeather = MutableLiveData<CurrentWeather>()
    val currentWeather = _currentWeather

        fun getForecast(
            longitude: Double,
            latitude: Double,
            timeZone: String
        ) {
            viewModelScope.launch {
                when (val result = forecastRepository.getForecast(longitude, latitude, timeZone)) {
                    is Success -> _currentWeather.postValue(result.data.currentWeather)
                    is Error -> _errors.postValue(result.message)
                }
            }
        }

}