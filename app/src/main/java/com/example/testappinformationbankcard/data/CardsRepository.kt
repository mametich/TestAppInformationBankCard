package com.example.testappinformationbankcard.data

import android.util.Log
import com.example.testappinformationbankcard.api.CardsApiService
import com.example.testappinformationbankcard.data.model.CardInfo
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException

class CardsRepository @Inject constructor(
    private val cardsApiService: CardsApiService,
    private val cardsDao: CardsDao
) {
    fun getCardById(bin: String): Flow<CardInfo?> = flow {
        try {
            val cachedCard = cardsDao.getCardByBin(bin).first()
            if (cachedCard != null) {
                Log.d("CardsRepository", "Found card in cache")
                emit(cachedCard)
                return@flow
            }

            val networkCard = getCardFromNetwork(bin)

            networkCard?.let { card ->
                cardsDao.insertCard(card)
                emit(card)
            } ?: emit(null)

        } catch (e: Exception) {
            Log.e("CardsRepository", "Error getting card: ${e.message}", e)
            emit(null)
        }
    }

    private suspend fun getCardFromNetwork(bin: String): CardInfo? {
        return try {
            val response = withContext(Dispatchers.IO) {
                cardsApiService.getCardInfo(bin)
            }

            Log.d("CardsRepository", "Response code: ${response.code()}")
            Log.d("CardsRepository", "Response headers: ${response.headers()}")

            when {
                response.isSuccessful -> {
                    response.body()?.also { cardInfo ->
                        Log.d("CardsRepository", """
                            Successful response:
                            Type: ${cardInfo.type}
                            Brand: ${cardInfo.brand}
                            Country: ${cardInfo.country?.name}
                            Bank: ${cardInfo.bank?.name}
                        """.trimIndent())
                    }
                }
                else -> {
                    val errorBody = response.errorBody()?.use { it.string() }
                    Log.e("CardsRepository", "Error ${response.code()}: $errorBody")
                    null
                }
            }
        } catch (e: IOException) {
            Log.e("CardsRepository", "Network error: ${e.message}", e)
            null
        } catch (e: Exception) {
            Log.e("CardsRepository", "Unexpected error: ${e.message}", e)
            null
        }
    }

    fun getAllCards(): Flow<List<CardInfo>> {
        return cardsDao.getAllCards()
    }
}

