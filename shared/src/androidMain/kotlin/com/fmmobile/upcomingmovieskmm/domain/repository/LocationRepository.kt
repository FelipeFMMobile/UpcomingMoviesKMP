package com.fmmobile.upcomingmovieskmm.domain.repository

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.fmmobile.upcomingmovieskmm.AppContext
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

private lateinit var fusedLocationClient: FusedLocationProviderClient

actual object LocationRepository {
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    @SuppressLint("MissingPermission")
    actual suspend fun make(): LocationInfo {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(AppContext.get())
        return try {
            val result = fusedLocationClient.lastLocation.await()
            return LocationInfo(result.latitude, result.longitude)
        } catch (e: Exception) {
            e.printStackTrace()
            return LocationInfo(0.0,0.0)
        }
    }

    fun isLocationPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            AppContext.get().applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}

