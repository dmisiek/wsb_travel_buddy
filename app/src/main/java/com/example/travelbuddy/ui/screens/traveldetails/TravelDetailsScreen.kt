package com.example.travelbuddy.ui.screens.traveldetails

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.example.travelbuddy.ui.common.BackButton
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelDetailsScreen(
    popToPreviousScreen: () -> Unit,
    viewModel: TravelDetailsViewModel = koinViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    BackButton(popToPreviousScreen)
                },
                title = {
                    Text("Szczegóły podróży")
                }
            )
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            item {
                Text("Details screen")
            }
        }
    }
}
