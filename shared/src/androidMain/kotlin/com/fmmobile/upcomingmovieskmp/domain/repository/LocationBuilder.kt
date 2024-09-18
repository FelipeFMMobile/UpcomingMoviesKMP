package com.fmmobile.upcomingmovieskmp.domain.repository

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.fmmobile.upcomingmovieskmp.AppContext
import com.fmmobile.upcomingmovieskmp.AppContextResumeCallback
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource

private lateinit var fusedLocationClient: FusedLocationProviderClient

actual object LocationBuilder {
    private var locationCallback: LocationCallback? = null
    private val cancellationTokenSource = CancellationTokenSource()
    object contextCallback : AppContextResumeCallback {
        override fun onPermissionGranted() {
            locationCallback?.let { make(it) }
        }

        override fun onPermissionDenied() {
            locationCallback?.onLocationError(Exception("Permission not granted"))
        }
    }

    @SuppressLint("MissingPermission")
    actual fun make(callback: LocationCallback) {
        locationCallback = callback
        AppContext.registerOnStartedCallback(contextCallback)
        if (!isLocationPermissionGranted()) {
           startLocationPermissionRequest()
           return
        }
        fusedLocationClient = LocationServices
            .getFusedLocationProviderClient(AppContext.get())
        fusedLocationClient.getCurrentLocation(
            com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        ).addOnSuccessListener { location ->
            if (location != null) {
                locationCallback?.onLocationReceived(
                    LocationInfo(location.latitude, location.longitude))
            } else {
                locationCallback?.onLocationError(Exception("No location available"))
            }
        }.addOnFailureListener {
            // Handle any errors
            locationCallback?.onLocationError(Exception("Failed to retrieve location"))
        }
    }

    private fun startLocationPermissionRequest() {
        AppContext.requestPermissionLauncher?.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            AppContext.get().applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    actual fun stopLocationUpdates() {
    }
}
