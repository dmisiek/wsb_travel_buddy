package com.example.travelbuddy.ui.screens.traveldetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.travelbuddy.ui.common.BackButton
import com.example.travelbuddy.ui.screens.traveldetails.components.TravelPhotoBox
import com.example.travelbuddy.ui.screens.traveldetails.components.WeatherTile
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelDetailsScreen(
    popToPreviousScreen: () -> Unit,
    viewModel: TravelDetailsViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                BackButton(popToPreviousScreen)
            }, title = {
                Text(state.resource?.name ?: "Szczegóły podróży")
            })
        }) { padding ->
        LazyColumn(contentPadding = padding) {

            item {
                Column {
                    Text(
                        "Pogoda",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(8.dp)
                    )

                    WeatherTile()

                    Text(
                        "Zdjęcia",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(top = 32.dp, bottom = 16.dp)
                    )
                }
            }

            state.resource?.photos?.let {
                items(it) { photo ->
                    TravelPhotoBox(
                        photo,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                }
            }
        }
    }
}
