package com.example.travelbuddy.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.travelbuddy.ui.screens.home.components.TravelCell
import org.koin.androidx.compose.koinViewModel
import kotlin.collections.map

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("Home")
            }, actions = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle, contentDescription = ""
                    )
                }
            })
        }) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.padding(padding),
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    "Moje podróże",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp, 8.dp)
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    "Odkrywaj",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp, 8.dp)
                )
            }

            items(state.travels) { travel ->
                TravelCell(travel)
            }
        }
    }
}
