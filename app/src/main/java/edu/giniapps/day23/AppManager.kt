package edu.giniapps.day23

import android.app.Application
import edu.giniapps.day23.networking.buildRemoteApi

class AppManager : Application() {
    companion object {
        //private lateinit var instance: AppManager

        val remoteApi by lazy {
            buildRemoteApi()
        }
    }

    /*override fun onCreate() {
        super.onCreate()

        instance = this
    }*/
}