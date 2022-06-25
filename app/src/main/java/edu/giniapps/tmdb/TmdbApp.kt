package edu.giniapps.tmdb

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.HiltAndroidApp

private const val SHARED_PREFERENCES_NAME = "TMDBAppSP"

@HiltAndroidApp
class TmdbApp : Application() {
    override fun onCreate() {
        super.onCreate()

        SHARED_PREFERENCES = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    companion object {

        lateinit var SHARED_PREFERENCES: SharedPreferences

        const val DB_HAS_GENRES_KEY = "GENRES"
        const val DB_LAST_UPDATE = "MOVIES"
    }
}