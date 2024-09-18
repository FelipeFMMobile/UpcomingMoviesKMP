package com.fmmobile.upcomingmovieskmp.domain.repository

import io.github.aakira.napier.Napier
import kotlinx.cinterop.useContents
import platform.CoreLocation.*
import platform.Foundation.NSError
import platform.darwin.NSObject


actual object LocationBuilder {
    private val locationHandler = LocationHandler()
    actual fun make(callback: LocationCallback) {
        locationHandler.delegate = LocationDelegate(callback)
        locationHandler.requestLocation()
    }

    actual fun stopLocationUpdates() {
        locationHandler.stop()
    }
}

private class LocationHandler {
    lateinit var locationManager: CLLocationManager
    lateinit var delegate: LocationDelegate
    private val authorizationStatus: CLAuthorizationStatus
        get() = CLLocationManager().authorizationStatus

    fun requestLocation() {
        locationManager = CLLocationManager()
        locationManager.desiredAccuracy = kCLLocationAccuracyBestForNavigation
        locationManager.setDelegate(delegate)

        if (CLLocationManager.locationServicesEnabled()) {
            when (authorizationStatus) {
                kCLAuthorizationStatusAuthorizedWhenInUse.toInt(),
                kCLAuthorizationStatusAuthorizedAlways.toInt() -> {
                    locationManager.requestLocation()
                    Napier.i("Request requestLocation")
                }
                else -> {
                    locationManager.requestWhenInUseAuthorization()
                    Napier.i("Request requestWhenInUseAuthorization")
                }
            }
        } else {
            stop()
            Napier.i("Location not enabled")
            delegate.callback.onLocationError(throw IllegalStateException("Location disable"))

        }
    }

    fun stop() {
        locationManager.stopUpdatingLocation()
    }

}

internal class LocationDelegate(val callback: LocationCallback): NSObject(), 
    CLLocationManagerDelegateProtocol {

    @Suppress("CONFLICTING_OVERLOADS")
    override fun locationManager(manager: CLLocationManager, didStartMonitoringForRegion: CLRegion) { }

    @Suppress("CONFLICTING_OVERLOADS")
    override fun locationManager(manager: CLLocationManager, didEnterRegion: CLRegion) { }

    @Suppress("CONFLICTING_OVERLOADS")
    override fun locationManager(manager: CLLocationManager, didExitRegion: CLRegion) { }

    // Delegate method for receiving location updates
    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
        val location = didUpdateLocations.last() as? CLLocation
        location?.coordinate?.useContents {
            val latitude = this.latitude
            val longitude = this.longitude
            callback.onLocationReceived(LocationInfo(latitude, longitude))
        }
    }


    // Delegate method for handling authorization status changes
    override fun locationManager(
        manager: CLLocationManager,
        didChangeAuthorizationStatus: CLAuthorizationStatus
    ) {
        Napier.i("Request didChangeAuthorizationStatus")
        when (didChangeAuthorizationStatus) {
            kCLAuthorizationStatusAuthorizedWhenInUse.toInt(),
            kCLAuthorizationStatusAuthorizedAlways.toInt() -> {
                manager.requestLocation()
                Napier.i("Location authorized")
            }

            kCLAuthorizationStatusDenied.toInt(),
            kCLAuthorizationStatusRestricted.toInt() -> {
                callback.onLocationError(Throwable("Location Denied"))
                Napier.e("Location access denied or restricted")
            }

            kCLAuthorizationStatusNotDetermined.toInt() -> {
                callback.onLocationError(Throwable("Location Denied"))
                Napier.e("Location access not determined")
            }

            else -> {
                Napier.e("Unknown location authorization status")
                callback.onLocationError(Throwable("Location Denied"))
                throw IllegalStateException("Unknown location authorization status")
            }
        }
    }

    // Delegate method for handling location errors
    override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
        Napier.e("Failed to get location: ${didFailWithError.localizedDescription}")
    }
}