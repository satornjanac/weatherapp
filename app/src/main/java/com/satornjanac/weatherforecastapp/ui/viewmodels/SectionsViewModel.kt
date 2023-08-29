package com.satornjanac.weatherforecastapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satornjanac.weatherforecastapp.model.CurrentWeather
import com.satornjanac.weatherforecastapp.model.Section
import com.satornjanac.weatherforecastapp.model.Sections
import com.satornjanac.weatherforecastapp.networking.Error
import com.satornjanac.weatherforecastapp.networking.Success
import com.satornjanac.weatherforecastapp.repo.ForecastRepository
import com.satornjanac.weatherforecastapp.repo.SectionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SectionsViewModel @Inject constructor(private val sectionsRepository: SectionsRepository) :
    ViewModel() {

    // showing all error dialogs regarding mixes
    private val _errors = MutableLiveData<String?>()
    val errors: LiveData<String?> = _errors
    private val _sectionList = MutableLiveData<List<Section>>()
    val sectionList = _sectionList

        fun getSections() {
            viewModelScope.launch {
                when (val result = sectionsRepository.getSections()) {
                    is Success -> _sectionList.postValue(result.data.sections)
                    is Error -> _errors.postValue(result.message)
                }
            }
        }

}