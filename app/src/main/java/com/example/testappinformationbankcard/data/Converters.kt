package com.example.testappinformationbankcard.data

import androidx.room.TypeConverter
import com.example.testappinformationbankcard.data.model.BankInfo
import com.example.testappinformationbankcard.data.model.CountryInfo
import kotlinx.serialization.json.Json
import com.example.testappinformationbankcard.data.model.NumberInfo

class Converters {
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromNumberInfo(numberInfo: NumberInfo?): String? {
        return numberInfo?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun toNumberInfo(jsonString: String?): NumberInfo? {
        return jsonString?.let { json.decodeFromString<NumberInfo>(it) }
    }

    @TypeConverter
    fun fromCountryInfo(countryInfo: CountryInfo?): String? {
        return countryInfo?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun toCountryInfo(jsonString: String?): CountryInfo? {
        return jsonString?.let { json.decodeFromString<CountryInfo>(it) }
    }

    @TypeConverter
    fun fromBankInfo(bankInfo: BankInfo?): String? {
        return bankInfo?.let { json.encodeToString(it) }
    }

    @TypeConverter
    fun toBankInfo(jsonString: String?): BankInfo? {
        return jsonString?.let { json.decodeFromString<BankInfo>(it) }
    }
}


