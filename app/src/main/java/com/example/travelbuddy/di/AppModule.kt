package com.example.travelbuddy.di

import com.example.travelbuddy.ui.screens.home.HomeViewModel
import com.example.travelbuddy.ui.screens.login.LoginViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::LoginViewModel)
}
