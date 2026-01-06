package com.dksystem.tallertechtest.repository

import com.dksystem.tallertechtest.models.WeatherResponse

class RepositoryServices(private val locationApi: LocationApi, private val weatherApi: WeatherApi) {
    suspend fun searchWeather(name: String): WeatherResponse?{
        val locations = locationApi.getLocation(name)
        val location = locations.results?.firstOrNull() ?: return null
        return weatherApi.getWeather(location.latitude, location.longitude)
    }
}