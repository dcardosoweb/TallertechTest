package com.dksystem.tallertechtest.repository

import com.dksystem.tallertechtest.models.GeoResponse
import com.dksystem.tallertechtest.models.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApi {
    @GET("v1/search")
    suspend fun getLocation(@Query("name") name: String): GeoResponse
}

interface WeatherApi {
    @GET("v1/forecast?current_weather=true")
    suspend fun getWeather(@Query("latitude") latitude: Double, @Query("longitude") longitude: Double): WeatherResponse
}
