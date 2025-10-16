package com.example.travelbuddy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.travelbuddy.ui.screens.home.HomeScreen
import com.example.travelbuddy.ui.screens.login.LoginScreen
import com.example.travelbuddy.ui.screens.register.RegisterScreen
import com.example.travelbuddy.ui.screens.traveldetails.TravelDetailsScreen
import com.example.travelbuddy.ui.screens.travelform.TravelFormScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

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
                        pushToLoginScreen = { backStack.add(LoginRoute) },
                        pushToRegisterScreen = { backStack.add(RegisterRoute) },
                        pushToDetailsScreen = { backStack.add(TravelDetailsRoute(id = it)) },
                        pushToFormScreen = { backStack.add(TravelFormRoute) }
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

            is RegisterRoute -> {
                NavEntry(key) {
                    RegisterScreen(
                        popToPreviousScreen = backStack::removeLastOrNull
                    )
                }
            }

            is TravelDetailsRoute -> {
                NavEntry(key) {
                    TravelDetailsScreen(
                        popToPreviousScreen = backStack::removeLastOrNull,
                        viewModel = koinViewModel {
                            parametersOf(key.id)
                        }
                    )
                }
            }

            is TravelFormRoute -> {
                NavEntry(key) {
                    TravelFormScreen(
                        popToPreviousScreen = { createdId ->
                            backStack.removeLastOrNull()
                            if (createdId != null) {
                                backStack.add(TravelDetailsRoute(createdId))
                            }
                        },
                    )
                }
            }

            else -> throw RuntimeException("Invalid NavKey")
        }
    }
}
