package com.dksystem.tallertechtest.repository

import com.dksystem.tallertechtest.models.LocationDto
import com.dksystem.tallertechtest.models.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApi {
    @GET("/search")
    suspend fun getLocation(@Query("name") name: String): List<LocationDto>
}

interface WeatherApi {
    @GET("/forecast?current_weather=true")
    suspend fun getWeather(@Query("latitude") latitude: Double, @Query("longitude") longitude: Double): WeatherDto
}
