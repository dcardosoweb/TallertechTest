package com.dksystem.tallertechtest.models

sealed class UiStateModel {
    data object Idle: UiStateModel()
    data object Loading: UiStateModel()
    data class Success(val weatherDto: WeatherResponse): UiStateModel()
    data class Error(val message: String): UiStateModel()
    data object Empty: UiStateModel()
}