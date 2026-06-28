package com.dyshiuk.eventapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dyshiuk.eventapp.network.EventDto

@Composable
fun EventDetailsScreen(
    event: EventDto?,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Button(onClick = onBackClick) {
            Text("Back")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (event == null) {
            Text("Event not found")
            return
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = event.title)
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "Location: ${event.location ?: "Not specified"}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Start: ${event.startDate}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "End: ${event.endDate}")
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = event.description ?: "No description")
            }
        }
    }
}