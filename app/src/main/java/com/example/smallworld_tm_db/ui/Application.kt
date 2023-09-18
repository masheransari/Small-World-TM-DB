package com.example.smallworld_tm_db.ui

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class Application : Application(){
    companion object {
        var application: Application? = null
            private set
        val applicationContext: Context
            get() = application!!.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }

}