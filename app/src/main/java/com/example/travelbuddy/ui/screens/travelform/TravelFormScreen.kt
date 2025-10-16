package com.example.travelbuddy.ui.screens.travelform

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.travelbuddy.ui.common.BackButton
import com.example.travelbuddy.ui.screens.travelform.components.PhotoField
import com.example.travelbuddy.ui.screens.travelform.models.FormResult
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelFormScreen(
    popToPreviousScreen: (String?) -> Unit,
    viewModel: TravelFormViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(state.result) {
        coroutineScope.launch {
            state.result?.let {
                when (it) {
                    is FormResult.Created -> {
                        snackbarHostState.showSnackbar(
                            message = "Podróż została dodana!",
                            actionLabel = "OK",
                            duration = SnackbarDuration.Short
                        )
                        popToPreviousScreen(it.id)
                    }

                    is FormResult.Error -> {
                        snackbarHostState.showSnackbar(
                            message = "Wystąpił problem przy dodawaniu podróży. Prosimy spróbować ponownie.",
                            duration = SnackbarDuration.Long
                        )
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                BackButton({ popToPreviousScreen(null) })
            }, title = {
                Text("Dodawanie podróży")
            })
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp),
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = state.name,
                onValueChange = viewModel::nameChanged,
                label = {
                    Text("Nazwa podróży")
                },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(64.dp))
            PhotoField(
                value = state.photo,
                onValueChange = viewModel::photoChanged,
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = state.description,
                onValueChange = viewModel::descriptionChanged,
                label = {
                    Text("Opis")
                },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(64.dp))
            if (state.isPending) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = viewModel::submit,
                    enabled = state.isValid(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                ) {
                    Text("Dodaj")
                }
            }

        }
    }
}
