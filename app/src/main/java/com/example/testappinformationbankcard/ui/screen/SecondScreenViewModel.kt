package com.example.testappinformationbankcard.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappinformationbankcard.data.CardsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecondScreenViewModel @Inject constructor(
    private val cardsRepository: CardsRepository
) : ViewModel() {

    private val _historyState = MutableStateFlow<List<CardHistoryItem>>(emptyList())
    val historyState: StateFlow<List<CardHistoryItem>> = _historyState.asStateFlow()

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            try {
                cardsRepository.getAllCards().collect { cards ->
                    _historyState.value = cards.map { card ->
                        CardHistoryItem(
                            scheme = card.scheme,
                            type = card.type ?: "Unknown",
                            brand = card.brand ?: "Unknown",
                            countryName = card.country?.name ?: "Unknown",
                            countryLatitude = card.country?.latitude?.toString() ?: "Unknown",
                            countryLongitude = card.country?.longitude?.toString() ?: "Unknown",
                            bankName = card.bank?.name ?: "Unknown"
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("SecondScreenViewModel", "Error loading history: ${e.message}", e)
                _historyState.value = emptyList()
            }
        }
    }
}

data class CardHistoryItem(
    val scheme: String,
    val type: String,
    val brand: String,
    val countryName: String,
    val countryLatitude: String,
    val countryLongitude: String,
    val bankName: String
)
