package com.satornjanac.weatherforecastapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.satornjanac.weatherforecastapp.model.ui.DisplayItems
import com.satornjanac.weatherforecastapp.networking.core.Error
import com.satornjanac.weatherforecastapp.networking.core.Success
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
    private val _viewsAndData = MutableLiveData<List<DisplayItems>>()
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

    fun getForecastFlow(
        longitude: Double,
        latitude: Double,
        timeZone: String
    ) {
        viewModelScope.launch {
            repository.getForecastAsFlow(longitude, latitude, timeZone).collect {
                when(it) {
                    is Success -> _viewsAndData.postValue(it.data)
                    is Error -> _errors.postValue(it.message)
                }
            }
        }
    }

}