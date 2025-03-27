package com.example.testappinformationbankcard.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testappinformationbankcard.data.CardsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cardsRepository: CardsRepository
) : ViewModel() {

    private val _cardsUiState = MutableStateFlow<CardUiState>(CardUiState())
    val cardsUiState: StateFlow<CardUiState> = _cardsUiState

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun getCardFromApi(bin: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null

                cardsRepository.getCardById(bin)
                    .catch { e ->
                        Log.e("MainViewModel", "Error in flow: ${e.message}", e)
                        _error.value = "Произошла ошибка: ${e.message}"
                        _cardsUiState.update { currentState ->
                            currentState.copy(
                                scheme = "Error: ${e.message}",
                                brand = "Unknown",
                                numberLength = "Unknown",
                                numberLuhn = "Unknown",
                                type = "Unknown",
                                prepaid = "Unknown",
                                countryName = "Unknown",
                                countryLatitude = "Unknown",
                                countryLongitude = "Unknown",
                                bankName = "Unknown",
                                bankUrl = "Unknown",
                                bankPhone = "Unknown",
                                bankCity = "Unknown"
                            )
                        }
                    }
                    .collect { cardInfo ->
                        if (cardInfo != null) {
                            Log.d("MainViewModel", "Updating UI with card info")
                            _cardsUiState.update { currentState ->
                                currentState.copy(
                                    scheme = cardInfo.scheme,
                                    brand = cardInfo.brand ?: "Unknown",
                                    numberLength = cardInfo.number?.length?.toString() ?: "Unknown",
                                    numberLuhn = if(cardInfo.number?.luhn == true) "Yes" else "No",
                                    type = cardInfo.type ?: "Unknown",
                                    prepaid = if(cardInfo.prepaid == true) "Yes" else "No",
                                    countryName = cardInfo.country?.name ?: "Unknown",
                                    countryLatitude = cardInfo.country?.latitude?.toString() ?: "Unknown",
                                    countryLongitude = cardInfo.country?.longitude?.toString() ?: "Unknown",
                                    bankName = cardInfo.bank?.name ?: "Unknown",
                                    bankUrl = cardInfo.bank?.url ?: "Unknown",
                                    bankPhone = cardInfo.bank?.phone ?: "Unknown",
                                    bankCity = cardInfo.bank?.city ?: "Unknown"
                                )
                            }
                        } else {
                            Log.e("MainViewModel", "Card info is null")
                            _error.value = "Не удалось получить информацию о карте"
                            _cardsUiState.update { currentState ->
                                currentState.copy(
                                    scheme = "Error: Data not available",
                                    brand = "Unknown",
                                    numberLength = "Unknown",
                                    numberLuhn = "Unknown",
                                    type = "Unknown",
                                    prepaid = "Unknown",
                                    countryName = "Unknown",
                                    countryLatitude = "Unknown",
                                    countryLongitude = "Unknown",
                                    bankName = "Unknown",
                                    bankUrl = "Unknown",
                                    bankPhone = "Unknown",
                                    bankCity = "Unknown"
                                )
                            }
                        }
                    }
            } finally {
                _isLoading.value = false
            }
        }
    }
}

data class CardUiState(
    val bin: Int = 0,
    val scheme: String = "",
    val brand: String = "",
    val numberLength: String = "",
    val numberLuhn: String = "",
    val type: String = "",
    val prepaid: String = "",
    val countryName: String = "",
    val countryLatitude: String = "",
    val countryLongitude: String = "",
    val bankName: String = "",
    val bankUrl: String = "",
    val bankPhone: String = "",
    val bankCity: String = ""
)