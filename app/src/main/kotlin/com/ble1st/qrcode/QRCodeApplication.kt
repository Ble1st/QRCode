package com.ble1st.qrcode

import android.app.Application
import android.content.pm.ApplicationInfo
import timber.log.Timber
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class QRCodeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (isDebugBuild()) {
            Timber.plant(Timber.DebugTree())
        }
    }
    
    private fun isDebugBuild(): Boolean {
        return (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
    }
}
