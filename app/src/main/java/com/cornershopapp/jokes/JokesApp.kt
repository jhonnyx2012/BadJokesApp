package com.cornershopapp.jokes

import android.app.Application
import com.cornershopapp.core.logging.LogcatLogger

class JokesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            LogcatLogger.initialize()
        }
    }
}