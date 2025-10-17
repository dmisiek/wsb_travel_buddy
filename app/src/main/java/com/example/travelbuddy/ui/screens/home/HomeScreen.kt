package com.example.travelbuddy.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.RocketLaunch
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.travelbuddy.ui.screens.home.components.AccountTile
import com.example.travelbuddy.ui.screens.home.components.ButtonCell
import com.example.travelbuddy.ui.screens.home.components.TravelCell
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    pushToLoginScreen: () -> Unit,
    pushToRegisterScreen: () -> Unit,
    pushToDetailsScreen: (String) -> Unit,
    pushToFormScreen: () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val user by viewModel.user.collectAsStateWithLifecycle()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(user) {
        if (user != null) viewModel.fetchTravels()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    "Travel Buddy",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                )
                if (user != null) AccountTile(user = user!!)
                Spacer(modifier = Modifier.weight(1f))
                if (user == null) TextButton(
                    pushToRegisterScreen,
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                        .height(48.dp),
                ) {
                    Text("Stwórz konto")
                }
                Button(
                    onClick = if (user != null) viewModel::logout else pushToLoginScreen,
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .padding(bottom = 32.dp)
                        .fillMaxWidth()
                        .height(48.dp),
                ) {
                    Text(if (user != null) "Wyloguj" else "Zaloguj")
                }
            }
        },
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Home")
                    },
                    navigationIcon = {
                        IconButton({
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Menu,
                                contentDescription = "Menu",
                            )
                        }
                    },
                )
            },
            floatingActionButton = {
                AnimatedVisibility(
                    visible = user != null,
                    enter = scaleIn(),
                    exit = scaleOut(),
                ) {
                    FloatingActionButton(
                        onClick = pushToFormScreen,
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = null)
                    }
                }
                if (user != null) FloatingActionButton(pushToFormScreen) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Dodaj podróż",
                    )
                }
            },
        ) { padding ->
            PullToRefreshBox(
                isRefreshing = state.loadState == LoadState.RefreshLoading,
                onRefresh = viewModel::fetchTravels,
                modifier = Modifier.padding(padding)
            ) {
                if (state.isPending()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {

                        if (user != null) {
                            item(span = { GridItemSpan(maxLineSpan) }) {
                                Text(
                                    "Moje podróże",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier
                                        .padding(16.dp, 8.dp)
                                        .padding(top = 24.dp)
                                )
                            }

                            if (state.userTravels.isNotEmpty()) {
                                items(state.userTravels) { travel ->
                                    TravelCell(
                                        travel,
                                        modifier = Modifier.clickable(onClick = {
                                            pushToDetailsScreen(travel.id)
                                        }),
                                    )
                                }
                            } else {
                                item(span = { GridItemSpan(maxLineSpan) }) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(
                                            Icons.Outlined.RocketLaunch,
                                            contentDescription = "Empty",
                                            tint = MaterialTheme.colorScheme.outlineVariant,
                                            modifier = Modifier.padding(16.dp),
                                        )
                                        Text(
                                            "Nie dodałeś jeszcze żadnej podróży",
                                            style = MaterialTheme.typography.labelLarge,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                }
                            }


                            if (state.userTravels.isNotEmpty() && !state.areAllUserPhotosLoaded)
                                item {
                                    ButtonCell(
                                        onClick = {},
                                        icon = Icons.Default.MoreHoriz,
                                    )
                                }
                        }


                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Text(
                                "Odkrywaj",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier
                                    .padding(16.dp, 8.dp)
                                    .padding(top = if (user != null) 48.dp else 0.dp)
                            )
                        }

                        items(state.exploreTravels) { travel ->
                            TravelCell(
                                travel,
                                modifier = Modifier.clickable(onClick = { pushToDetailsScreen(travel.id) }),
                            )
                        }
                    }
                }
            }
        }
    }
}


private fun HomeState.isPending(): Boolean {
    return loadState == LoadState.InitialLoading || loadState == LoadState.RefreshLoading
}
