package com.ssafy.pickit

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PickitApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}