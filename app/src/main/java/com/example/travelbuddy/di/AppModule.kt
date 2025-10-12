package com.example.travelbuddy.di

import com.example.travelbuddy.ui.screens.home.HomeViewModel
import com.example.travelbuddy.ui.screens.login.LoginViewModel
import com.example.travelbuddy.ui.screens.traveldetails.TravelDetailsViewModel
import com.example.travelbuddy.ui.screens.travelform.TravelFormViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::TravelDetailsViewModel)
    viewModelOf(::TravelFormViewModel)
    viewModelOf(::LoginViewModel)
}
