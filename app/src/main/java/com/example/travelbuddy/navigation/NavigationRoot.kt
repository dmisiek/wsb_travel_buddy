package com.example.travelbuddy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.travelbuddy.ui.screens.home.HomeScreen
import com.example.travelbuddy.ui.screens.login.LoginScreen
import com.example.travelbuddy.ui.screens.traveldetails.TravelDetailsScreen
import com.example.travelbuddy.ui.screens.travelform.TravelFormScreen

@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val backStack = rememberNavBackStack(HomeRoute)

    NavDisplay(
        backStack = backStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
        ),
        modifier = modifier
    ) { key ->
        when (key) {
            is HomeRoute -> {
                NavEntry(key) {
                    HomeScreen(
                        pushToLoginScreen = {
                            backStack.add(LoginRoute)
                        },
                        pushToDetailsScreen = {
                            backStack.add(TravelDetailsRoute(id = it))
                        },
                        pushToFormScreen = {
                            backStack.add(TravelFormRoute)
                        }
                    )
                }
            }

            is TravelDetailsRoute -> {
                NavEntry(key) {
                    TravelDetailsScreen(
                        popToPreviousScreen = backStack::removeLastOrNull,
                    )
                }
            }

            is TravelFormRoute -> {
                NavEntry(key) {
                    TravelFormScreen(
                        popToPreviousScreen = backStack::removeLastOrNull,
                    )
                }
            }

            is LoginRoute -> {
                NavEntry(key) {
                    LoginScreen(
                        popToPreviousScreen = backStack::removeLastOrNull,
                    )
                }
            }

            else -> throw RuntimeException("Invalid NavKey")
        }
    }
}
