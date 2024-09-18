package com.fmmobile.upcomingmovieskmp.domain.repository

public interface LocationCallback {
    fun onLocationReceived(locationInfo: LocationInfo)
    fun onLocationError(error: Throwable)
}

expect object LocationBuilder {
    fun make(callback: LocationCallback)
    fun stopLocationUpdates()
}

data class  LocationInfo(
    val lat: Double,
    val long: Double
)