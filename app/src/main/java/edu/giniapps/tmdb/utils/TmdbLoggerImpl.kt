package edu.giniapps.tmdb.utils

import android.util.Log
import javax.inject.Inject

class TmdbLoggerImpl @Inject constructor() : TmdbLogger {
    override fun logError(tag: String, log: String) {
        if (TMDB_LOGGER_ENABLED)
            Log.e("$LOGGER_TAG/$tag", log)
    }

    override fun logDebug(tag: String, log: String) {
        if (TMDB_LOGGER_ENABLED)
            Log.d("$LOGGER_TAG/$tag", log)
    }
}