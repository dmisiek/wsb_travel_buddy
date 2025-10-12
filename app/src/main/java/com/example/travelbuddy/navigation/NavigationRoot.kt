package com.example.travelbuddy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.travelbuddy.ui.screens.home.HomeScreen
import com.example.travelbuddy.ui.screens.login.LoginScreen

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
                NavEntry(key = key) {
                    HomeScreen()
                }
            }

            is LoginRoute -> {
                NavEntry(key = key) {
                    LoginScreen()
                }
            }

            else -> throw RuntimeException("Invalid NavKey")
        }
    }
}
