package com.example.travelbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.travelbuddy.ui.screens.login.LoginScreen
import com.example.travelbuddy.ui.theme.TravelBuddyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelBuddyTheme {
                LoginScreen()
            }
        }
    }
}
