package com.example.travelbuddy.di

import com.example.travelbuddy.data.auth.repositories.AuthRepository
import com.example.travelbuddy.data.auth.repositories.AuthRepositoryImpl
import com.example.travelbuddy.ui.screens.home.HomeViewModel
import com.example.travelbuddy.ui.screens.login.LoginViewModel
import com.example.travelbuddy.ui.screens.traveldetails.TravelDetailsViewModel
import com.example.travelbuddy.ui.screens.travelform.TravelFormViewModel
import com.google.firebase.auth.FirebaseAuth
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single<AuthRepository> { AuthRepositoryImpl(get()) }

    viewModelOf(::HomeViewModel)
    viewModelOf(::TravelDetailsViewModel)
    viewModelOf(::TravelFormViewModel)
    viewModelOf(::LoginViewModel)
}
