package com.example.testappinformationbankcard.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "card_info")
data class CardInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val scheme: String = "",
    val number: NumberInfo? = null,
    val type: String? = null,
    val brand: String? = null,
    val prepaid: Boolean? = null,
    val country: CountryInfo? = null,
    val bank: BankInfo? = null
)

@Serializable
data class NumberInfo(
    val length: Int? = null,
    val luhn: Boolean? = null
)

@Serializable
data class CountryInfo(
    val name: String? = null,
    val currency: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)

@Serializable
data class BankInfo(
    val name: String? = null,
    val url: String? = null,
    val phone: String? = null,
    val city: String? = null
)
