package com.example.travelbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.travelbuddy.navigation.NavigationRoot
import com.example.travelbuddy.ui.theme.TravelBuddyTheme
import org.koin.androidx.compose.KoinAndroidContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelBuddyTheme {
                KoinAndroidContext {
                    NavigationRoot(
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}
