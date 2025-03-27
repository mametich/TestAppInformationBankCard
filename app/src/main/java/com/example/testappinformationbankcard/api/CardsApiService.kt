package com.example.testappinformationbankcard.api

import com.example.testappinformationbankcard.data.model.CardInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CardsApiService {

    @GET("{bin}")
    suspend fun getCardInfo(
        @Path("bin") bin: String,
        @Header("Accept-Version") version: String = "3"
    ): Response<CardInfo>
}