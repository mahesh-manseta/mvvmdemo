package com.maheshmanseta.mvvm

import android.app.Application
import com.maheshmanseta.mvvm.utils.AppPreferences

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppPreferences.init(this)
    }
}