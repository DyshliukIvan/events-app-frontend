package com.dyshiuk.eventapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dyshiuk.eventapp.network.EventDto

@Composable
fun EventListScreen(
    events: List<EventDto>,
    onEventClick: (Long) -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    var showOnlyPrague by remember { mutableStateOf(false) }

    val filteredEvents = events.filter { event ->
        val matchesSearch =
            event.title.contains(searchText, ignoreCase = true) ||
                    event.location.contains(searchText, ignoreCase = true)

        val matchesFilter =
            !showOnlyPrague || event.location == "Prague"

        matchesSearch && matchesFilter
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Events")

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Search events") }
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { showOnlyPrague = !showOnlyPrague }
        ) {
            Text(if (showOnlyPrague) "Show all" else "Only Prague")
        }


        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(filteredEvents) { event ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onEventClick(event.id) },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = event.title)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Location: ${event.location}")
                        Text(text = "Date: ${event.date}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = event.description)
                    }
                }
            }
        }
    }
}