package com.example.mealio

import android.app.Application
import com.google.android.gms.ads.MobileAds
import kotlin.concurrent.thread

class MealioApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        thread(name = "MobileAdsInit") {
            MobileAds.initialize(this) {}
        }
    }
}
