package com.dksystem.tallertechtest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dksystem.tallertechtest.models.UiStateModel
import com.dksystem.tallertechtest.models.WeatherDto
import com.dksystem.tallertechtest.viewModel.WeatherViewModel

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel
) {
    var query by rememberSaveable { mutableStateOf("") }
    val state by viewModel.state.observeAsState(UiStateModel.Idle)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it }, // ✅ only updates local state
                modifier = Modifier.weight(1f),
                label = { Text("Search city") },
                singleLine = true
            )

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = {
                    viewModel.onClick(query)
                },
                enabled = query.trim().isNotEmpty()
            ) {
                Text("Search")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        when (val s = state) {
            UiStateModel.Idle -> Unit

            UiStateModel.Loading -> {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            UiStateModel.Empty -> Text("No results found")

            is UiStateModel.Error -> Text(
                text = s.message,
                color = MaterialTheme.colorScheme.error
            )

            is UiStateModel.Success -> WeatherResultCard(data = s.weatherDto)
        }
    }
}

@Composable
private fun WeatherResultCard(data: WeatherDto) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "${data.temperature}°", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = data.weathercode?.toString() ?: "N/A", style = MaterialTheme.typography.bodyMedium)
        }
    }
}