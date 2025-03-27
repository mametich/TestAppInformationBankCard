package com.example.testappinformationbankcard.di

import android.content.Context
import com.example.testappinformationbankcard.api.CardsApiService
import com.example.testappinformationbankcard.data.CardDatabase
import com.example.testappinformationbankcard.data.CardsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CardModule {

    @Provides
    @Singleton
    fun provideCardDatabase(@ApplicationContext context: Context): CardDatabase {
        return CardDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideCardsDao(cardDatabase: CardDatabase) = cardDatabase.cardsDao()
}
