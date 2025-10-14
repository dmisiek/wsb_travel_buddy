package com.example.travelbuddy.ui.screens.traveldetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.travelbuddy.domain.travels.models.TravelPhoto

@Composable
fun TravelPhotoBox(photo: TravelPhoto, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(4.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    MaterialTheme.colorScheme.surfaceContainer
                )
        ) {
            Text(photo.url ?: "")
        }
        photo.description?.let {
            Text(
                "\"$it\"",
                modifier = Modifier
                    .padding(4.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainerLow)
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}
