package edu.giniapps.tmdb.utils

const val LOGGER_TAG = "TmdbApp"
const val TMDB_LOGGER_ENABLED = true

interface TmdbLogger {
    fun logError(tag: String, log: String)

    fun logDebug(tag: String, log: String)
}