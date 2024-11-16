package com.example.myapplication
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager

object NetworkUtils {

    // Function to check if the device has internet permission and is connected
    fun checkInternetPermissionAndConnection(context: Context): Boolean {
        // Check if the app has internet permission (this is not a runtime permission)
        val hasInternetPermission = ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.INTERNET
        ) == PackageManager.PERMISSION_GRANTED

        // If the app doesn't have the internet permission, request it
        if (!hasInternetPermission) {
            Toast.makeText(context, "App does not have INTERNET permission!", Toast.LENGTH_SHORT).show()
            return false
        }

        // Now check if the device has an active internet connection
        return isInternetAvailable(context)
    }

    // Function to check if the device is connected to the internet
    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
            return networkCapabilities != null &&
                    (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) &&
                    networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}
