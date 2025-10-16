package com.example.travelbuddy.di

import com.example.travelbuddy.data.auth.repositories.AuthRepository
import com.example.travelbuddy.data.auth.repositories.AuthRepositoryImpl
import com.example.travelbuddy.data.travels.repositories.TravelRepository
import com.example.travelbuddy.data.travels.repositories.FirebaseTravelRepository
import com.example.travelbuddy.ui.screens.home.HomeViewModel
import com.example.travelbuddy.ui.screens.login.LoginViewModel
import com.example.travelbuddy.ui.screens.register.RegisterViewModel
import com.example.travelbuddy.ui.screens.traveldetails.TravelDetailsViewModel
import com.example.travelbuddy.ui.screens.travelform.TravelFormViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseStorage.getInstance() }

    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<TravelRepository> { FirebaseTravelRepository(get(), get(), get()) }

    viewModelOf(::HomeViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::TravelDetailsViewModel)
    viewModelOf(::TravelFormViewModel)
}
