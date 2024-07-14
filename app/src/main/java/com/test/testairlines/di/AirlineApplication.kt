package com.test.testairlines.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AirlineApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}