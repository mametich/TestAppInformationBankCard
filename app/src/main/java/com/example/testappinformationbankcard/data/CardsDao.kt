package com.example.testappinformationbankcard.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testappinformationbankcard.data.model.CardInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface CardsDao {
    @Query("SELECT * FROM card_info")
    fun getAllCards(): Flow<List<CardInfo>>

    @Query("SELECT * FROM card_info WHERE scheme = :bin LIMIT 1")
    fun getCardByBin(bin: String): Flow<CardInfo?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: CardInfo)
}