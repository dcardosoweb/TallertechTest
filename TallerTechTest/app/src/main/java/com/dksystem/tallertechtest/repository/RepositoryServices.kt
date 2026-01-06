package com.dksystem.tallertechtest.repository

import com.dksystem.tallertechtest.models.WeatherDto

class RepositoryServices(private val locationApi: LocationApi, private val weatherApi: WeatherApi) {
    suspend fun searchWeather(name: String): WeatherDto?{
        val locations = locationApi.getLocation(name)
        val location = locations.firstOrNull() ?: return null
        return weatherApi.getWeather(location.latitude, location.longitude)
    }
}