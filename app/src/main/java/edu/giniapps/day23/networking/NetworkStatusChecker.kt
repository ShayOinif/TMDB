package edu.giniapps.day23.networking

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkStatusChecker(
    private val connectivityManager: ConnectivityManager?,
) {
    // Inline methods are copied to the client's class.
    inline fun performIfConnectedToInternet(action: () -> Unit) =
        if (hasInternetConnection()) {
            action()

            true
        } else
            false

    fun hasInternetConnection(): Boolean {
        val network = connectivityManager?.activeNetwork ?: return false

        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        // If capability is WiFi or Cellular or VPN then true.
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
    }
}