package com.yxl.student_guide

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StudentGuideApp: Application(){
    override fun onCreate() {
        super.onCreate()
        MapKitFactory.setApiKey("")
    }
}