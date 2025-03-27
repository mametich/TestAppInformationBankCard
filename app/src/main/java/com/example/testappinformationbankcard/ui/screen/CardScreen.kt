package com.example.testappinformationbankcard.ui.screen

import android.graphics.Color
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CardInfoScreen(viewModel: MainViewModel) {
    val cardUiState by viewModel.cardsUiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    var binInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = binInput,
            onValueChange = { binInput = it },
            label = { Text("Введите BIN карты") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { viewModel.getCardFromApi(binInput) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Получить информацию")
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        error?.let { errorMessage ->
            Text(
                text = errorMessage,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        CardInfoSection("SHEME", cardUiState.scheme)
        CardInfoSection("BRAND", cardUiState.brand)
        CardInfoSection("LENGTH", cardUiState.numberLength)
        CardInfoSection("LUHN", cardUiState.numberLuhn)
        CardInfoSection("TYPE", cardUiState.type)
        CardInfoSection("PREPAID", cardUiState.prepaid)
        
        Text(
            text = "COUNTRY",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        CardInfoSection("NAME", cardUiState.countryName)
        CardInfoSection("latitude", cardUiState.countryLatitude)
        CardInfoSection("longitude", cardUiState.countryLongitude)
        
        Text(
            text = "BANK",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        CardInfoSection("Name", cardUiState.bankName)
        CardInfoSection("URL", cardUiState.bankUrl)
        CardInfoSection("phone", cardUiState.bankPhone)
        CardInfoSection("City", cardUiState.bankCity)
    }
}

@Composable
private fun CardInfoSection(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}