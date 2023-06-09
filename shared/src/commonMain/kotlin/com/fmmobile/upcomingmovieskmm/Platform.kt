package com.fmmobile.upcomingmovieskmm

import com.fmmobile.upcomingmovieskmm.di.appModule
import com.fmmobile.upcomingmovieskmm.di.remoteModule

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

// start a KoinApplication in Global context
fun appModule() = listOf(appModule, remoteModule)