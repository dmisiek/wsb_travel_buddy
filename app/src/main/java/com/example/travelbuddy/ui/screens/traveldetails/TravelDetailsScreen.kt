package com.example.travelbuddy.ui.screens.traveldetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.example.travelbuddy.ui.common.BackButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelDetailsScreen(
    popToPreviousScreen: () -> Unit,
    viewModel: TravelDetailsViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(state.deleteState) {
        when (state.deleteState) {
            DeleteState.INITIAL, DeleteState.PENDING -> {}
            DeleteState.DONE -> {
                popToPreviousScreen()
            }

            DeleteState.ERROR -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Wystąpił problem przy usuwaniu podróży. Prosimy spróbować ponownie.",
                        duration = SnackbarDuration.Short,
                    )
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(navigationIcon = {
                BackButton(popToPreviousScreen)
            }, title = {
                Text(state.resource?.name ?: "Szczegóły podróży")
            }, actions = {
                if (state.resource?.belongsToAuthedUser == true) {
                    IconButton(viewModel::delete) {
                        if (state.deleteState == DeleteState.PENDING) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        } else {
                            Icon(
                                if (state.deleteState == DeleteState.DONE) Icons.Outlined.Check
                                else Icons.Outlined.Delete,
                                contentDescription = "Delete",
                            )
                        }
                    }
                }
            })
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 2.dp),
        ) {
            state.resource?.let {
                if (it.belongsToAuthedUser) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                    ) {
                        Icon(
                            if (it.isPublic) Icons.Outlined.Visibility
                            else Icons.Outlined.VisibilityOff,
                            contentDescription = "Public state",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier
                                .size(24.dp)
                                .padding(end = 8.dp),
                        )
                        Text(
                            if (it.isPublic) "Zasób widoczny publicznie"
                            else "Zasób prywatny",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.labelMedium,
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
            AsyncImage(
                model = state.resource?.photoUri,
                contentDescription = "Remote image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            state.resource?.description?.let {
                Text(
                    if (it.isNotBlank()) "\"$it\"" else "Nie dodano opisu",
                    style =
                        if (it.isNotBlank()) MaterialTheme.typography.bodyMedium
                        else MaterialTheme.typography.labelLarge,
                    color =
                        if (it.isNotBlank()) MaterialTheme.colorScheme.onSurface
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainerLow)
                        .fillMaxWidth()
                        .padding(20.dp)
                )
            }
        }
    }
}
