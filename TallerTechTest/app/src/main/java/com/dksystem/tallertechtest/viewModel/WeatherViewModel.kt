package com.dksystem.tallertechtest.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dksystem.tallertechtest.models.UiStateModel
import com.dksystem.tallertechtest.repository.RepositoryServices
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WeatherViewModel(private val repositoryServices: RepositoryServices): ViewModel() {

    private val _state = MutableLiveData<UiStateModel>(UiStateModel.Idle)
    val state: LiveData<UiStateModel> = _state

    private var searchJob: Job? = null

    fun onClick(paramValue: String){
        val query = paramValue.trim()
        searchJob?.cancel()
        if(query.isEmpty()){
            _state.value = UiStateModel.Idle
            return
        }
        searchJob = viewModelScope.launch {
            _state.value = UiStateModel.Loading
            val result = repositoryServices.searchWeather(query)
            _state.value = result?.let { UiStateModel.Success(it) } ?: UiStateModel.Error("Error")
        }
    }
}