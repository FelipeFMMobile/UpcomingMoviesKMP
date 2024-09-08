package com.fmmobile.upcomingmovieskmm.domain.repository

expect object LocationRepository {
    suspend fun make(): LocationInfo
}

data class  LocationInfo(
    val lat: Double,
    val long: Double
)