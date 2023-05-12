package com.fmmobile.upcomingmovieskmm

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform