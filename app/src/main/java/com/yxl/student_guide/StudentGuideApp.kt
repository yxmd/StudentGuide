package com.yxl.student_guide

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StudentGuideApp: Application(){
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("3b316f43-0a57-41a8-bf69-2c5d12cd9bbf")
    }
}