package com.satornjanac.weatherforecastapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satornjanac.weatherforecastapp.model.SectionsWithForecast
import com.satornjanac.weatherforecastapp.networking.Error
import com.satornjanac.weatherforecastapp.networking.Success
import com.satornjanac.weatherforecastapp.repo.SectionsWithForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val repository: SectionsWithForecastRepository) :
    ViewModel() {

    // showing all error dialogs regarding mixes
    private val _errors = MutableLiveData<String?>()
    val errors: LiveData<String?> = _errors
    private val _viewsAndData = MutableLiveData<SectionsWithForecast>()
    val viewsAndData = _viewsAndData

        fun getForecast(
            longitude: Double,
            latitude: Double,
            timeZone: String
        ) {
            viewModelScope.launch {
                when (val result = repository.getSectionsWithForecast(longitude, latitude, timeZone)) {
                    is Success -> _viewsAndData.postValue(result.data)
                    is Error -> _errors.postValue(result.message)
                }
            }
        }

}