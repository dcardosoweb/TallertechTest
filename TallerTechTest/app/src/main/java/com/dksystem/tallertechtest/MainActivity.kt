package com.dksystem.tallertechtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.dksystem.tallertechtest.viewModel.WeatherViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val app = application as App
        val vm = app.factory.create(WeatherViewModel::class.java)
        setContent {
            MaterialTheme {
                WeatherScreen(viewModel = vm)
            }
        }
    }
}