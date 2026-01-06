package com.dksystem.tallertechtest

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dksystem.tallertechtest.repository.LocationApi
import com.dksystem.tallertechtest.repository.NetworkConfig
import com.dksystem.tallertechtest.repository.RepositoryServices
import com.dksystem.tallertechtest.repository.WeatherApi
import com.dksystem.tallertechtest.viewModel.WeatherViewModel

class App : Application() {

    lateinit var weatherRepository: RepositoryServices
        private set
    lateinit var factory: WeatherViewModelFactory
        private set

    override fun onCreate() {
        super.onCreate()

        val client = NetworkConfig.okHttpClient()

        val geoApi = NetworkConfig
            .retrofit("https://geocoding-api.open-meteo.com/", client)
            .create(LocationApi::class.java)

        val weatherApi = NetworkConfig
            .retrofit("https://api.open-meteo.com/", client)
            .create(WeatherApi::class.java)

        weatherRepository = RepositoryServices(geoApi, weatherApi)
        factory = WeatherViewModelFactory(weatherRepository)
    }

    class WeatherViewModelFactory(
        private val repo: RepositoryServices
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return WeatherViewModel(repo) as T
        }
    }
}
