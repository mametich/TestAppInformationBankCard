package com.example.testappinformationbankcard

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CardApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d("CardApplication", "Application onCreate called")
    }
}