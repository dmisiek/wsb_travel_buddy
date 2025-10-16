package com.example.travelbuddy.ui.screens.traveldetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelDetailsScreen(
    popToPreviousScreen: () -> Unit,
    viewModel: TravelDetailsViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    BackButton(popToPreviousScreen)
                },
                title = {
                    Text(state.resource?.name ?: "Szczegóły podróży")
                },
            )
        }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 2.dp),
        ) {
            TravelPhotoBox(
                photo = state.resource?.photoUri,
                description = state.resource?.description,
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }
    }
}
