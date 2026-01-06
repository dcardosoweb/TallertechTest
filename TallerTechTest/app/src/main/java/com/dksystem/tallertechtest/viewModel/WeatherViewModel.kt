package com.dksystem.tallertechtest.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dksystem.tallertechtest.models.UiStateModel
import com.dksystem.tallertechtest.repository.RepositoryServices
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class WeatherViewModel(private val repositoryServices: RepositoryServices): ViewModel() {

    private val _state = MutableLiveData<UiStateModel>(UiStateModel.Idle)
    val state: LiveData<UiStateModel> = _state

    private var searchJob: Job? = null

    fun OnQueryChange(paramValue: String) {
        val query = paramValue.trim()
        if (query.isEmpty()) {
            _state.value = UiStateModel.Idle
            return
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            _state.value = UiStateModel.Loading

            try {
                val result = repositoryServices.searchWeather(query)
                _state.value =
                    if (result == null) UiStateModel.Empty else UiStateModel.Success(result)
            } catch (e: Exception) {
                _state.value = UiStateModel.Error(e.message ?: "Unexpected error")
            }
        }
    }
}