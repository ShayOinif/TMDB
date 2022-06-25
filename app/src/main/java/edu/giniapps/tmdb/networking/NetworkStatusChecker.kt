package edu.giniapps.tmdb.networking

import edu.giniapps.tmdb.models.TmdbNetworkThrowable

interface NetworkStatusChecker {
    fun hasInternetConnection(): Boolean
}

inline fun <T : Any> NetworkStatusChecker.performIfConnectedToInternet(action: () -> Result<T>): Result<T> =
    if (hasInternetConnection()) {
        action()
    } else
        Result.failure(TmdbNetworkThrowable)