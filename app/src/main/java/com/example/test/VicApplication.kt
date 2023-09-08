package com.example.test

import android.app.Application
import timber.log.Timber

/**
 * Vic application
 *
 * @constructor Create empty Vic application
 */
class VicApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}