package com.test.testairlines

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AirlineApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}