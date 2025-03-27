package com.example.testappinformationbankcard.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SecondScreen(
    viewModel: SecondScreenViewModel
) {
    val history by viewModel.historyState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (history.isEmpty()) {
            Text(
                text = "История запросов пуста",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(history) { card ->
                    CardHistoryItem(card)
                }
            }
        }
    }
}

@Composable
private fun CardHistoryItem(card: CardHistoryItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Sheme: ${card.scheme}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("Type: ${card.type}")
            Text("Brand: ${card.brand}")
            
            Text(
                text = "Country",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Text("Name: ${card.countryName}")
            Text("Широта: ${card.countryLatitude}")
            Text("Долгота: ${card.countryLongitude}")
            
            Text(
                text = "Bank",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Text("Name: ${card.bankName}")
        }
    }
}